package com.steelIndustry.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steelIndustry.framework.dao.EntityJpaDao;
import com.steelIndustry.model.Settings;

@Repository
public interface SettingsDao extends EntityJpaDao<Settings, Integer> {
    @Query("select s from Settings s")
    public Settings getSettings();

    @Modifying
    @Query("update Settings s set s.shareSwitch=:shareSwitch where id=1")
    public int updateShareSwitch(@Param("shareSwitch") short shareSwitch);

    @Modifying
    @Query("update Settings s set s.isCheckStore=:isCheckStore where id=1")
    public int updateIsCheckStore(@Param("isCheckStore") short isCheckStore);

    @Modifying
    @Query("update Settings s set s.isCheckProject=:isCheckProject where id=1")
    public int updateIsCheckProject(@Param("isCheckProject") short isCheckProject);

    @Modifying
    @Query("update Settings s set s.isCheckCard=:isCheckCard where id=1")
    public int updateIsCheckCard(@Param("isCheckCard") short isCheckCard);

    @Modifying
    @Query("update Settings s set s.isCheckWork=:isCheckWork where id=1")
    public int updateIsCheckWork(@Param("isCheckWork") short isCheckWork);

    @Modifying
    @Query("update Settings s set s.mainPostPoints=:mainPostPoints where id=1")
    public int updateMainPostPoints(@Param("mainPostPoints") int mainPostPoints);

    @Modifying
    @Query("update Settings s set s.replyingPoints=:replyingPoints where id=1")
    public int updateReplyingPoints(@Param("replyingPoints") int replyingPoints);

    @Modifying
    @Query("update Settings s set s.homePageAdType=:homePageAdType where id=1")
    public int updateHomePageAdType(@Param("homePageAdType") String homePageAdType);

    @Modifying
    @Query("update Settings s set s.listPageAdType=:listPageAdType where id=1")
    public int updateListPageAdType(@Param("listPageAdType") String listPageAdType);

    @Modifying
    @Query("update Settings s set s.detailPageAdType=:detailPageAdType where id=1")
    public int updateDetailPageAdType(@Param("detailPageAdType") String detailPageAdType);
}