INSERT INTO `user_details`(email,first_name,last_name) VALUES
('abc@dummy.com','John','Doe');
INSERT INTO `user_details`(email,first_name,last_name) VALUES
('def@dummy.com','John1','Doe1');
INSERT INTO `user_details`(email,first_name,last_name) VALUES
('ghi@dummy.com','John2','Doe2');

INSERT INTO `role` (role_name, description) VALUES
('admin','Admin users'),('super_admin','Super admin user'),('user','User');

INSERT INTO `user_role_mapping` (user_id,role_id) VALUES
(1,1),(2,2),(3,3),(1,3),(2,3);

INSERT INTO `permission` (permission_name, description) VALUES
('read','Read data'),('write','Write data'),('delete','Delete data');

INSERT INTO `feature` (feature_name, description) VALUES
('Design','Design'),('Verify','Verification'),('Approve','Approval');

INSERT INTO `role_permission_mapping`(role_id,feature_id,permission_id) VALUES
(1,1,1),(1,1,2),(2,1,1),(2,1,2),(2,1,3),(3,1,1),(3,1,2),
(1,2,1),(2,2,1),(2,2,2),(2,2,3),(1,3,1),(2,3,1),(2,3,2),(2,3,3);