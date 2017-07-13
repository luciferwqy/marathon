use marathon;

drop table if exists  `personalinfo`;
CREATE TABLE `personalinfo` (
   `account` varchar(50) NOT NULL,
   `pwd` varchar(50) NOT NULL,
   `name` varchar(50) DEFAULT NULL,
   `nationality` varchar(50) DEFAULT NULL COMMENT '国籍',
   `IDType` int(11) DEFAULT NULL COMMENT '证件类型0：身份证',
   `IDNumber` varchar(50) DEFAULT NULL,
   `sex` varchar(45) DEFAULT NULL,
   `birthDay` varchar(45) DEFAULT NULL,
   `mobilPhone` varchar(11) DEFAULT NULL,
   `email` varchar(50) DEFAULT NULL,
   `residence` varchar(450) DEFAULT NULL COMMENT '居住地',
   `address` varchar(450) DEFAULT NULL COMMENT '通信地址',
   `bloodType` varchar(45) DEFAULT NULL COMMENT '血型',
   `occupation` varchar(45) DEFAULT NULL COMMENT '职业',
   `education` varchar(45) DEFAULT NULL COMMENT '教育程度',
   `university` varchar(100) DEFAULT NULL COMMENT '大学',
   `urgentPerson` varchar(50) DEFAULT NULL COMMENT '紧急联系人',
   `urgentPhone` varchar(11) DEFAULT NULL COMMENT '紧急联系电话',
   `backupPhone` varchar(11) DEFAULT NULL COMMENT '备用电话',
   `backupMail` varchar(50) DEFAULT NULL COMMENT '备用邮箱',
   `createtime` DATETIME NULL COMMENT '注册时间',
   PRIMARY KEY (`account`),
   UNIQUE KEY `account_UNIQUE` (`account`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 drop table if exists  `marathonrace`;
 CREATE TABLE `marathonrace` (
   `account` varchar(50) NOT NULL,
   `marathonName` varchar(450) DEFAULT NULL COMMENT '赛事名称',
   `achievement` varchar(50) DEFAULT NULL COMMENT '成绩',
   `certificatePath` varchar(450) DEFAULT NULL COMMENT '比赛证书路径',
   PRIMARY KEY (`account`),
   UNIQUE KEY `account_UNIQUE` (`account`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 
 drop table if exists  `registration`;
 CREATE TABLE `marathon`.`registration` (
  `matchId` INT NOT NULL COMMENT '比赛ID',
  `account` VARCHAR(50) NOT NULL COMMENT '账号',
  `auditState` INT(11) default 0 COMMENT '审核是否通过0：未通过；1：通过',
  `payState` INT(11) default 0 COMMENT '是否支付0：未支付；1：已支付',
  `groupId` INT NOT NULL COMMENT '组别',
  `competitionNo` VARCHAR(45) NULL COMMENT '参赛号',
  `raceOrderId` VARCHAR(45) NULL COMMENT '比赛支付订单号',
  `achievement` VARCHAR(50) NULL COMMENT '比赛成绩',
  `ranking` int(11) NULL COMMENT '比赛名次',
  `createtime` DATETIME NULL COMMENT '报名时间',
  `updatetime` DATETIME NULL,
  `remark` VARCHAR(500) NULL COMMENT '备注，审核不通过时',
  PRIMARY KEY (`matchId`, `account`),
  UNIQUE KEY(`matchId`, `competitionNo`),
  UNIQUE KEY(`raceOrderId`)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  
  drop table if exists auth_operator;

 CREATE TABLE `auth_operator` (
   `optid` int(11) NOT NULL AUTO_INCREMENT,
   `badge` varchar(15) DEFAULT NULL COMMENT '用户名',
   `password` varchar(50) DEFAULT NULL,
   `optName` varchar(200) DEFAULT NULL COMMENT '姓名',
   `tel` varchar(20) DEFAULT NULL COMMENT '电话',
   `mail` varchar(20) DEFAULT NULL COMMENT '邮箱',
   `createtime` datetime DEFAULT NULL COMMENT '创建时间',
   `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
   `opt` varchar(20) DEFAULT NULL COMMENT '操作人',
   PRIMARY KEY (`optid`),
   UNIQUE KEY `optid` (`optid`) USING BTREE
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists auth_func;

CREATE TABLE `auth_func` (
   `funcid` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(20) DEFAULT NULL COMMENT '功能名称',
   `url` varchar(200) DEFAULT NULL COMMENT '链接',
   `funcgroupID` int(11) DEFAULT NULL COMMENT '功能组',
   `parentid` int(11) DEFAULT NULL COMMENT '父级ID',
   `tag` varchar(20) DEFAULT NULL COMMENT '小标签',
   `createtime` datetime DEFAULT NULL COMMENT '创建时间',
   `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
   `opt` varchar(20) DEFAULT NULL COMMENT '操作人',
   PRIMARY KEY (`funcid`),
   UNIQUE KEY `funcid` (`funcid`) USING BTREE
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
drop table if exists auth_operatorrole;

CREATE TABLE `auth_operatorrole` (
   `operatorid` int(11) DEFAULT NULL COMMENT '操作员id',
   `roleid` int(11) DEFAULT NULL COMMENT '角色id',
   `createtime` datetime DEFAULT NULL COMMENT '创建时间',
   `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
   `opt` varchar(20) DEFAULT NULL COMMENT '操作人'
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
drop table if exists auth_role;

CREATE TABLE `auth_role` (
   `roleid` int(11) NOT NULL AUTO_INCREMENT,
   `roleName` varchar(20) DEFAULT NULL COMMENT '角色名称',
   `roleState` varchar(2) DEFAULT NULL COMMENT '是否可用1=可用；0=不可用',
   `createtime` datetime DEFAULT NULL COMMENT '创建时间',
   `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
   `opt` varchar(20) DEFAULT NULL COMMENT '操作人',
   PRIMARY KEY (`roleid`),
   UNIQUE KEY `funcid` (`roleid`) USING BTREE
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 
 
 drop table if exists auth_rolefunc;

 CREATE TABLE `auth_rolefunc` (
   `roleid` int(11) DEFAULT NULL COMMENT '角色id',
   `funcid` int(11) DEFAULT NULL COMMENT '功能编号',
   `privilege` varchar(2) DEFAULT '1' COMMENT '权限',
   `createtime` datetime DEFAULT NULL COMMENT '创建时间',
   `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
   `opt` varchar(20) DEFAULT NULL COMMENT '操作人'
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 drop table if exists `marathon`.`match`;
 CREATE TABLE `marathon`.`match` (
  `matchId` INT NOT NULL AUTO_INCREMENT,
  `matchName` VARCHAR(200) NULL COMMENT '赛事名称',
  `startTime` DATETIME NULL COMMENT '开始时间',
  `regDeadline` DATETIME NULL COMMENT '报名截止日期',
  `state` INT(11) NULL COMMENT '状态',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `opt` varchar(20) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`matchId`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
 drop table if exists `marathon`.`matchgroup`;
 CREATE TABLE `marathon`.`matchgroup` (
  `groupId` INT NOT NULL AUTO_INCREMENT,
  `matchId` INT NOT NULL,
  `groupName` VARCHAR(200) NULL COMMENT '组别名称',
  `fee` VARCHAR(45) NULL COMMENT '报名费',
  `fee2` VARCHAR(45) NULL COMMENT '外籍报名费',
  `hasDraw` VARCHAR(2) NULL COMMENT '是否抽取参赛人员',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `opt` varchar(20) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`groupId`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  drop table if exists `marathon`.`news`;
  CREATE TABLE `marathon`.`news` (
  `newsId` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(500) NULL COMMENT '新闻标题',
  `fileName` VARCHAR(500) NULL COMMENT '新闻静态页面名称',
  `type` INT(11) NULL COMMENT '新闻类型0：官方新闻；1：比赛新闻。。。',
  `lang` varchar(20) NULL COMMENT '中英文',
  `createtime` datetime NULL,
  PRIMARY KEY (`newsId`)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  drop table if exists `marathon`.`orderinfo`;
  CREATE TABLE `marathon`.`orderinfo` (
  `orderId` varchar(100) NOT NULL COMMENT '订单号',
  `state` INT(11) default 0 COMMENT '状态0:未支付,1:已支付',
  `account` VARCHAR(50) NULL COMMENT '账号',
  `expressId` VARCHAR(45) NULL COMMENT '运单号',
  `carrierName` VARCHAR(50) NULL,
  `receiverName` VARCHAR(100) NULL,
  `receiverPhone` VARCHAR(45) NULL,
  `receiverProvince` VARCHAR(100) NULL,
  `receiverCity` VARCHAR(100) NULL,
  `receiverArea` VARCHAR(100) NULL,
  `receiverAddress` VARCHAR(450) NULL,
  `receiverZipCode` VARCHAR(100) NULL,
  `createtime` datetime NULL,
  `remark` VARCHAR(500) NULL,
  PRIMARY KEY (`orderid`)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  drop table if exists `marathon`.`ordergoods`;
  CREATE TABLE `marathon`.`ordergoods` (
  `goodsId` INT NOT NULL AUTO_INCREMENT,
  `orderId` VARCHAR(100) NOT NULL,
  `goodsName` VARCHAR(450) NULL,
  `quantity` INT(11) NULL,
  `size` VARCHAR(45) NULL,
  `color` VARCHAR(45) NULL,
  `amount` VARCHAR(45) NULL,
  `style` VARCHAR(100) NULL COMMENT '全程版、半程版、亲子版',
  `remark` VARCHAR(450) NULL,
  PRIMARY KEY (`goodsid`)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  drop table if exists `marathon`.`receivedetail`; 
  CREATE TABLE `marathon`.`receivedetail` (
  `receiveId` INT NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(100) NOT NULL,
  `receiverName` VARCHAR(100) NULL,
  `receiverPhone` VARCHAR(45) NULL,
  `receiverProvince` VARCHAR(100) NULL,
  `receiverCity` VARCHAR(100) NULL,
  `receiverArea` VARCHAR(100) NULL,
  `receiverAddress` VARCHAR(450) NULL,
  `receiverZipCode` VARCHAR(100) NULL,
  PRIMARY KEY (`receiveId`)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  
insert into auth_func values('1', '比赛管理', NULL, NULL, NULL, 'fa-desktop', NULL, NULL, NULL);
insert into auth_func values('2', '赛事管理', '/admin/system/matchMng/list.shtml', NULL, '1', NULL, NULL, NULL, NULL);
insert into auth_func values('3', '人员审核',  '/admin/system/reviewMng/list.shtml', NULL, '1', NULL, NULL, NULL, NULL);
insert into auth_func values('4', '订单管理', NULL, NULL, NULL, 'fa-desktop', NULL, NULL, NULL);
insert into auth_func values('5', '订单', '/admin/system/orderMng/list.shtml', NULL, '4', NULL, NULL, NULL, NULL);
insert into auth_func values('6', '系统管理', NULL, NULL, NULL, 'fa-desktop', NULL, NULL, NULL);
insert into auth_func values('7', '新闻发布', '/admin/system/newsMng/addNews.shtml', NULL, '6', NULL, NULL, NULL, NULL);
insert into auth_func values('8', '组别管理', '/admin/system/matchGroupMng/list.shtml', NULL, '1', NULL, NULL, NULL, NULL);
insert into auth_func values('9', '成绩录入', '/admin/system/reviewMng/fileUpload.shtml', NULL, '1', NULL, NULL, NULL, NULL);

insert into auth_rolefunc values('1', '1', '3', NULL, NULL, NULL);
insert into auth_rolefunc values('1', '2', '3', NULL, NULL, NULL);
insert into auth_rolefunc values('1', '3', '3', NULL, NULL, NULL);
insert into auth_rolefunc values('1', '4', '3', NULL, NULL, NULL);
insert into auth_rolefunc values('1', '5', '3', NULL, NULL, NULL);
insert into auth_rolefunc values('1', '6', '3', NULL, NULL, NULL);
insert into auth_rolefunc values('1', '7', '3', NULL, NULL, NULL);
insert into auth_rolefunc values('1', '8', '3', NULL, NULL, NULL);
insert into auth_rolefunc values('1', '9', '3', NULL, NULL, NULL);

insert into auth_role values('1', 'sysadmin', '1', NULL, NULL, NULL);

insert into auth_operatorrole values('1', '1', NULL, NULL, NULL);
insert into auth_operator values('1', '555', 'C81E728D9D4C2F636F067F89CC14862C', 'admin', '555', '555', NULL, NULL, NULL)
