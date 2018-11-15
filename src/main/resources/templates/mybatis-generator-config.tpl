<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
<generatorConfiguration>

    <context id="context1">
        <plugin type="org.mybatis.generator.plugins.AddLimitOffsetPlugin" />

        <jdbcConnection driverClass="￥DRIVER_CLASS￥"
            connectionURL="￥CONNECTION_URL￥"
            userId="￥USERNAME￥" password="￥PASSWORD￥" />
        <javaModelGenerator targetPackage="￥ROOT_PACKAGE￥.gen.pojo"
            targetProject="../￥PROJECT_NAME￥/src/main/java" />

        <sqlMapGenerator targetPackage="mapper"
            targetProject="../￥PROJECT_NAME￥/src/main/resources" />

        <javaClientGenerator targetPackage="￥ROOT_PACKAGE￥.gen.dao"
            targetProject="../￥PROJECT_NAME￥/src/main/java" type="XMLMAPPER" />

        <!-- tables to be generated -->
                        ￥TABLES_TO_GENERATE￥
    </context>
</generatorConfiguration>