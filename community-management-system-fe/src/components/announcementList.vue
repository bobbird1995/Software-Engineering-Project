<template>
  <div class="flex-box flex-col comp-announcement">
    <div class="announcement-header">社区公告 <br /></div>

    <div
      v-for="(a, i) in announcement"
      :key="i"
      class="flex-box jy-start as-start announcement-item"
    >
      <div v-if="a.coverUrl && a.coverUrl.length !== 0" class="cover-img">
        <img :src="a.coverUrl" alt="" />
      </div>
      <div class="content-box jy-start flex-box flex-col">
        <div class="title">{{ a.titleName }}</div>
        <div class="content-slice" v-html="a.content"></div>
        <div class="flex-box jy-between meta">
          <span>创建者：{{ a.creator }} ｜ 时间：{{ a.createTime }}</span>
        </div>
      </div>
    </div>

    <el-button @click="loadMoreAnnouncement" type="text">加载更多</el-button>
  </div>
</template>

<script>
import resErrorHandler from "../utils/resErrorHandler";
import { mapState } from "vuex";
// import announcementMock from "../mock/announcement";

export default {
  name: "announcementList",
  props: {
    editable: Boolean
  },
  data() {
    return {
      announcement: [],

      page: 1
    };
  },
  computed: {
    ...mapState(["userInfo"])
  },
  async mounted() {
    try {
      const res = await this.$axios.get(
        `/resident/getAnnouncementOfGrid/${this.userInfo.id}?page=${this.page}`
      );
      resErrorHandler(this, res);
      if (res.data.resultCode === "200") {
        console.log("获取公告成功！");
        console.log(res.data);
        this.announcement = res.data.data.dataList;
      }
    } catch (err) {
      this.$message.error(String(err));
    }
  },
  methods: {
    async loadMoreAnnouncement() {
      this.page += 1;
      try {
        const res = await this.$axios.get(
          `/resident/getAnnouncementOfGrid/${this.userInfo.id}?page=${this.page}`
        );
        console.log(res.data);
        resErrorHandler(this, res);
        if (res.data.resultCode === "200") {
          this.announcement = [...this.announcement, ...res.data.data.dataList];
        }
      } catch (err) {
        this.$message.error(String(err));
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.comp-announcement {
  width: 50%;
  box-sizing: border-box;

  .announcement-header {
    font-weight: bold;
    font-size: 20px;
    margin: 5px 0;

    a,
    a:visited,
    a:link {
      position: relative;
      font-weight: normal;
      font-size: 14px;
      border-bottom: 0 solid #2c3e50;
    }
    a::after {
      position: absolute;
      z-index: 1;
      left: 0;
      top: 20px;
      content: "";
      width: 0;
      height: 2px;
      background-color: #2c3e506e;
      border-radius: 40px;
      transition: width 0.4s ease;
    }
    a:hover {
      &::after {
        width: 100%;
      }
    }
  }

  .announcement-item {
    border: 1px solid rgba(0, 0, 0, 0.09);
    box-shadow: 0 5px 10px rgba(0, 0, 0, 0.15);
    box-sizing: border-box;
    width: 50%;
    padding: 20px;
    margin: 5px 0;

    .cover-img {
      img {
        width: 100px;
      }
    }
    .content-box {
      margin-left: 20px;
      align-items: start;
      .title {
        font-size: 16px;
        font-weight: bold;
      }
      .content-slice {
        text-align: left;
        font-size: 14px;
        color: dimgray;
      }
      .meta {
        width: 100%;
        margin-top: 10px;
        font-size: 12px;
        color: grey;
      }
    }
  }
}
</style>
