set @defaultId = (SELECT IFNULL(MAX(id),0) + 1 FROM sys_menu);
# select @defaultId;
INSERT INTO `sys_menu`(`id`, `parent_id`, `parent_ids`, `name`, `sort`, `url`, `target`, `type`, `icon`, `status`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`, `remarks`) VALUES ( @defaultId, 0, '/0/', '生成案例', 1000, '/sys/demo', 'main', 2, 'ant-design:tags-filled', 1, 'sys:demo:query', 1, now(), 1, now(), NULL);
INSERT INTO `sys_menu`(`parent_id`, `parent_ids`, `name`, `sort`, `url`, `target`, `type`, `icon`, `status`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`, `remarks`) VALUES ( @defaultId, CONCAT('/0/', @defaultId, '/'), '添加', 1010, NULL, 'main', 3, NULL, 1, 'sys:demo:save', 1, now(), 1, now(), NULL);
INSERT INTO `sys_menu`(`parent_id`, `parent_ids`, `name`, `sort`, `url`, `target`, `type`, `icon`, `status`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`, `remarks`) VALUES ( @defaultId, CONCAT('/0/', @defaultId, '/'), '修改', 1020, NULL, 'main', 3, NULL, 1, 'sys:demo:update', 1, now(), 1, now(), NULL);
INSERT INTO `sys_menu`(`parent_id`, `parent_ids`, `name`, `sort`, `url`, `target`, `type`, `icon`, `status`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`, `remarks`) VALUES ( @defaultId , CONCAT('/0/', @defaultId, '/'), '删除', 1030, NULL, 'main', 3, NULL, 0, 'sys:demo:delete', 1, now(), 1, now(), NULL);