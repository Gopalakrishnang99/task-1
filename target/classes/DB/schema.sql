CREATE TABLE IF NOT EXISTS `user_details` (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80)
);

CREATE TABLE IF NOT EXISTS `role` (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(80) NOT NULL,
    description VARCHAR(180)
);

CREATE TABLE IF NOT EXISTS `user_role_mapping` (
    id BIGINT AUTO_INCREMENT,
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES user_details(user_id),
    FOREIGN KEY (user_id) REFERENCES role(role_id)
);

CREATE TABLE IF NOT EXISTS `feature` (
    feature_id BIGINT AUTO_INCREMENT,
    feature_name VARCHAR(40) NOT NULL,
    description VARCHAR(180),
    PRIMARY KEY (feature_id)
);

CREATE TABLE IF NOT EXISTS `permission` (
    permission_id BIGINT AUTO_INCREMENT,
    permission_name VARCHAR(40) NOT NULL,
    description VARCHAR(180),
    PRIMARY KEY (permission_id)
);

CREATE TABLE IF NOT EXISTS `role_permission_mapping` (
    id BIGINT AUTO_INCREMENT,
    role_id BIGINT NOT NULL,
    feature_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    FOREIGN KEY (feature_id) REFERENCES feature(feature_id),
    FOREIGN KEY (permission_id) REFERENCES permission(permission_id)
);