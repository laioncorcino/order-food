CREATE TABLE order_item (
    order_item_id bigint(20) NOT NULL AUTO_INCREMENT,
    description varchar(255) DEFAULT NULL,
    quantity int(11) NOT NULL,
    order_id bigint(20) NOT NULL,
    PRIMARY KEY (order_item_id),
    FOREIGN KEY (order_id) REFERENCES `order`(order_id)
);