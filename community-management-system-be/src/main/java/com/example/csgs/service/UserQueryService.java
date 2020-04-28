package com.example.csgs.service;

import com.example.csgs.bean.CommunityInfo;
import com.example.csgs.bean.PageQuery;
import com.example.csgs.entity.UserEntity;

public interface UserQueryService {

    PageQuery allUserOfGrid(Long id, String page);

    Object multipleConditions(String userID,String userName,String community,Long id,String page);

    boolean deleteUser(Long uid);

}
