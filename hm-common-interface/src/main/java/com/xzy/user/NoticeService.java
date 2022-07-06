package com.xzy.user;


import com.xzy.pojo.user.HmNotice;

import java.util.List;

public interface NoticeService {

   void addNotice(HmNotice notice);

   void updateNotice(HmNotice notice);

   List<HmNotice> selNoticeAll();

   List<HmNotice> selNoticeUserId(Integer userId);

}
