package com.chris.pkg.manager.dao.mapper;

import com.chris.pkg.manager.dao.entity.ConfigEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by chris on 2017/8/10.
 */
public abstract interface PMConfigDaoMapper {

    public abstract void insertConfig(ConfigEntity paramConfigEntity) throws SQLException;

    public abstract List<ConfigEntity> queryConfig(ConfigEntity paramConfigEntity) throws SQLException;

    public abstract List<ConfigEntity> queryMenu() throws SQLException;

    public abstract List<ConfigEntity> queryNewestVersionByIdentifier(ConfigEntity paramConfigEntity) throws SQLException;

    public abstract List<ConfigEntity> queryVersions(ConfigEntity paramConfigEntity) throws SQLException;

    public abstract List<ConfigEntity> queryApps(ConfigEntity paramConfigEntity) throws SQLException;
}
