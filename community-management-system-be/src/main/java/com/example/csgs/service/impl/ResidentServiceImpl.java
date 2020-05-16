package com.example.csgs.service.impl;

import com.example.csgs.bean.CommunityInfo;
import com.example.csgs.bean.LoginState;
import com.example.csgs.bean.PageQuery;
import com.example.csgs.entity.Announcement;
import com.example.csgs.entity.CommunityInfoEntity;
import com.example.csgs.entity.UserEntity;
import com.example.csgs.mapper.AnnouncementMapper;
import com.example.csgs.mapper.ProfileMapper;
import com.example.csgs.mapper.UserMapper;
import com.example.csgs.service.ResidentService;
import com.example.csgs.service.UserSignService;
import com.example.csgs.utils.CalculatePageUtils;
import com.example.csgs.utils.JwtUtils;
import com.example.csgs.utils.RedisUtils;
import com.example.csgs.utils.SHA256Util;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@Service
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    ProfileMapper profileMapper;
    @Autowired
    AnnouncementMapper announcementMapper;
    /**
     * 场景：居民用户登陆，居民首页呈现自己所住小区名、房屋数量、停车位数量、居民数量
     * 和网格员向本小区发送的公告信息
     * 注意：当前id是居民用户的id
     * @param id   在user表中某一居民的id
     */
    @Override
    public CommunityInfo findResidentRPH(Long id) {
        CommunityInfoEntity communityInfoEntity = profileMapper.findResidentRPH(id);
        String communityName = communityInfoEntity.getCommunityName();
        Long numHouses = communityInfoEntity.getNumHouses();
        Long numResidents = communityInfoEntity.getNumResidents();
        Long numParkingSpaces = communityInfoEntity.getNumParkingSpaces();
        return new CommunityInfo(communityName, numHouses, numResidents, numParkingSpaces);
    }

    /**
     * 场景：在居民首页中获取自身所在网格区域的公告信息
     * @param id 在user表中某一居民的id
     * @param page 当前请求页数
     */
    @Override
    public PageQuery<Announcement> getAnnouncementOfGrid(Long id,String page) {
        int pageSize = 10;
        if (profileMapper.findGridIdIsExist(id) > 0) {//判断该用户所在小区现在是否有网格员管理
            CommunityInfoEntity communityInfoEntity = profileMapper.findUserIdByProfileId(id);
            Long gid = communityInfoEntity.getGridId().getUserId().getId();
            Page<Announcement> pageAble = PageHelper.startPage(Integer.parseInt(page), pageSize);
            PageHelper.orderBy("id ASC");//按id升序排列
            List<Announcement> announcementList = announcementMapper.findAnnouncementByCreator(gid);
            return CalculatePageUtils.
                    getPageInfo(Integer.parseInt(page), pageSize, pageAble, announcementList);
        }
        return null;
    }
}

