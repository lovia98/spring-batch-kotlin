# spring-batch-kotlin

* process : mysql에서 data를 가져와 json파일 생성 -> restApi호출시 response json data

* source data schema :
    
    
    
    CREATE TABLE `orders`.`orders_info` (
  `order_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_no` BIGINT(10) UNSIGNED NOT NULL DEFAULT '0',
  `payment_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
  `product_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
  `product_name` VARCHAR(225) NOT NULL DEFAULT '',
  `order_time` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`order_id`));
