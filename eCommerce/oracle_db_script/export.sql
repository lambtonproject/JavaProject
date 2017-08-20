--------------------------------------------------------
--  File created - Friday-August-18-2017   
--------------------------------------------------------
DROP TYPE "COMMERCE"."CATALOG_TYP";
DROP TYPE "COMMERCE"."CATEGORY_TYP";
DROP TYPE "COMMERCE"."COMPOSITE_CATEGORY_TYP";
DROP TYPE "COMMERCE"."CORPORATE_CUSTOMER_TYP";
DROP TYPE "COMMERCE"."CUSTOMER_TYP";
DROP TYPE "COMMERCE"."CUST_ADDRESS_TYP";
DROP TYPE "COMMERCE"."INVENTORY_LIST_TYP";
DROP TYPE "COMMERCE"."INVENTORY_TYP";
DROP TYPE "COMMERCE"."LEAF_CATEGORY_TYP";
DROP TYPE "COMMERCE"."ORDER_ITEM_LIST_TYP";
DROP TYPE "COMMERCE"."ORDER_ITEM_TYP";
DROP TYPE "COMMERCE"."ORDER_LIST_TYP";
DROP TYPE "COMMERCE"."ORDER_TYP";
DROP TYPE "COMMERCE"."PHONE_LIST_TYP";
DROP TYPE "COMMERCE"."PRODUCT_INFORMATION_TYP";
DROP TYPE "COMMERCE"."PRODUCT_REF_LIST_TYP";
DROP TYPE "COMMERCE"."SUBCATEGORY_REF_LIST_TYP";
DROP TYPE "COMMERCE"."WAREHOUSE_TYP";
DROP TABLE "COMMERCE"."CATEGORIES" cascade constraints;
DROP TABLE "COMMERCE"."CUSTOMERS" cascade constraints;
DROP TABLE "COMMERCE"."LOG" cascade constraints;
DROP TABLE "COMMERCE"."ORDERS" cascade constraints;
DROP TABLE "COMMERCE"."PRODUCTS" cascade constraints;
DROP TABLE "COMMERCE"."USERS" cascade constraints;
DROP SEQUENCE "COMMERCE"."CATEGORIES_SEQ";
DROP SEQUENCE "COMMERCE"."CUSTOMERS_SEQ";
DROP SEQUENCE "COMMERCE"."LOG_SEQ";
DROP SEQUENCE "COMMERCE"."ORDERS_SEQ";
DROP SEQUENCE "COMMERCE"."PRODUCTS_SEQ";
DROP SEQUENCE "COMMERCE"."USERS_SEQ";
DROP SYNONYM "COMMERCE"."COUNTRIES";
DROP SYNONYM "COMMERCE"."DEPARTMENTS";
DROP SYNONYM "COMMERCE"."EMPLOYEES";
DROP SYNONYM "COMMERCE"."JOBS";
DROP SYNONYM "COMMERCE"."JOB_HISTORY";
DROP SYNONYM "COMMERCE"."LOCATIONS";
--------------------------------------------------------
--  DDL for Type CATALOG_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."CATALOG_TYP" 
                                       
 UNDER composite_category_typ 
      ( 
    MEMBER FUNCTION getCatalogName RETURN VARCHAR2 
       , OVERRIDING MEMBER FUNCTION category_describe RETURN VARCHAR2 
      ); 
/
CREATE OR REPLACE TYPE BODY "COMMERCE"."CATALOG_TYP" AS
  OVERRIDING MEMBER FUNCTION category_describe RETURN varchar2 IS
  BEGIN
    RETURN 'catalog_typ';
  END;
  MEMBER FUNCTION getCatalogName RETURN varchar2 IS
  BEGIN
    -- Return the category name from the supertype
    RETURN self.category_name;
  END;
END;

/
--------------------------------------------------------
--  DDL for Type CATEGORY_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."CATEGORY_TYP" 
                                       
 AS OBJECT 
    ( category_name           VARCHAR2(50) 
    , category_description    VARCHAR2(1000) 
    , category_id             NUMBER(2) 
    , NOT instantiable 
      MEMBER FUNCTION category_describe RETURN VARCHAR2 
      ) 
  NOT INSTANTIABLE NOT FINAL
 ALTER TYPE category_typ
 ADD ATTRIBUTE (parent_category_id number(2)) CASCADE

/
--------------------------------------------------------
--  DDL for Type COMPOSITE_CATEGORY_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."COMPOSITE_CATEGORY_TYP" 
                                       
 UNDER category_typ 
      ( 
    subcategory_ref_list subcategory_ref_list_typ 
      , OVERRIDING MEMBER FUNCTION  category_describe RETURN VARCHAR2 
      ) 
  NOT FINAL; 
/
CREATE OR REPLACE TYPE BODY "COMMERCE"."COMPOSITE_CATEGORY_TYP" AS 
    OVERRIDING MEMBER FUNCTION category_describe RETURN VARCHAR2 IS 
    BEGIN 
      RETURN 'composite_category_typ'; 
    END; 
   END; 

/
--------------------------------------------------------
--  DDL for Type CORPORATE_CUSTOMER_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."CORPORATE_CUSTOMER_TYP" 
                                       
 UNDER customer_typ 
      ( account_mgr_id     NUMBER(6) 
      ); 

/
--------------------------------------------------------
--  DDL for Type CUSTOMER_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."CUSTOMER_TYP" 
 AS OBJECT
    ( customer_id        NUMBER(6)
    , cust_first_name    VARCHAR2(20)
    , cust_last_name     VARCHAR2(20)
    , cust_address       cust_address_typ
    , phone_numbers      phone_list_typ
    , nls_language       VARCHAR2(3)
    , nls_territory      VARCHAR2(40)
    , credit_limit       NUMBER(9,2)
    , cust_email         VARCHAR2(40)
    , cust_orders        order_list_typ
    ) 
NOT FINAL;

/
--------------------------------------------------------
--  DDL for Type CUST_ADDRESS_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."CUST_ADDRESS_TYP" 
                                        
  AS OBJECT
    ( street_address     VARCHAR2(40)
    , postal_code        VARCHAR2(10)
    , city               VARCHAR2(30)
    , state_province     VARCHAR2(10)
    , country_id         CHAR(2)
    );

/
--------------------------------------------------------
--  DDL for Type INVENTORY_LIST_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."INVENTORY_LIST_TYP" 
                                       
 AS TABLE OF inventory_typ;

/
--------------------------------------------------------
--  DDL for Type INVENTORY_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."INVENTORY_TYP" 
                                       
 AS OBJECT
    ( product_id          NUMBER(6) 
    , warehouse           warehouse_typ
    , quantity_on_hand    NUMBER(8)
    ) ;

/
--------------------------------------------------------
--  DDL for Type LEAF_CATEGORY_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."LEAF_CATEGORY_TYP" 
                                       
 UNDER category_typ 
    ( 
    product_ref_list    product_ref_list_typ 
    , OVERRIDING MEMBER FUNCTION  category_describe RETURN VARCHAR2 
    ); 
/
CREATE OR REPLACE TYPE BODY "COMMERCE"."LEAF_CATEGORY_TYP" AS 
    OVERRIDING MEMBER FUNCTION  category_describe RETURN VARCHAR2 IS 
    BEGIN 
       RETURN  'leaf_category_typ'; 
    END; 
   END; 

/
--------------------------------------------------------
--  DDL for Type ORDER_ITEM_LIST_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."ORDER_ITEM_LIST_TYP" 
                                       
 AS TABLE OF order_item_typ;

/
--------------------------------------------------------
--  DDL for Type ORDER_ITEM_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."ORDER_ITEM_TYP" 
                                       
 AS OBJECT
    ( order_id           NUMBER(12)
    , line_item_id       NUMBER(3)
    , unit_price         NUMBER(8,2)
    , quantity           NUMBER(8)
    , product_ref  REF   product_information_typ
    ) ;

/
--------------------------------------------------------
--  DDL for Type ORDER_LIST_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."ORDER_LIST_TYP" 
                                       
 AS TABLE OF order_typ;

/
--------------------------------------------------------
--  DDL for Type ORDER_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."ORDER_TYP" 
                                       
 AS OBJECT
    ( order_id           NUMBER(12)
    , order_mode         VARCHAR2(8)
    , customer_ref  REF  customer_typ
    , order_status       NUMBER(2)
    , order_total        NUMBER(8,2)
    , sales_rep_id       NUMBER(6)
    , order_item_list    order_item_list_typ
    ) ;

/
--------------------------------------------------------
--  DDL for Type PHONE_LIST_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."PHONE_LIST_TYP" 
                                        
  AS VARRAY(5) OF VARCHAR2(25);

/
--------------------------------------------------------
--  DDL for Type PRODUCT_INFORMATION_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."PRODUCT_INFORMATION_TYP" 
                                       
 AS OBJECT
    ( product_id           NUMBER(6)
    , product_name         VARCHAR2(50)
    , product_description  VARCHAR2(2000)
    , category_id          NUMBER(2)
    , weight_class         NUMBER(1)
    , warranty_period      INTERVAL YEAR(2) TO MONTH
    , supplier_id          NUMBER(6)
    , product_status       VARCHAR2(20)
    , list_price           NUMBER(8,2)
    , min_price            NUMBER(8,2)
    , catalog_url          VARCHAR2(50)
    , inventory_list       inventory_list_typ
    ) ;

/
--------------------------------------------------------
--  DDL for Type PRODUCT_REF_LIST_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."PRODUCT_REF_LIST_TYP" 
                                       
 AS TABLE OF number(6); 

/
--------------------------------------------------------
--  DDL for Type SUBCATEGORY_REF_LIST_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."SUBCATEGORY_REF_LIST_TYP" 
                                       
 AS TABLE OF REF category_typ; 

/
--------------------------------------------------------
--  DDL for Type WAREHOUSE_TYP
--------------------------------------------------------

  CREATE OR REPLACE TYPE "COMMERCE"."WAREHOUSE_TYP" 
                                       
 AS OBJECT
    ( warehouse_id       NUMBER(3)
    , warehouse_name     VARCHAR2(35)
    , location_id        NUMBER(4)
    ) ;

/
--------------------------------------------------------
--  DDL for Table CATEGORIES
--------------------------------------------------------

  CREATE TABLE "COMMERCE"."CATEGORIES" 
   (	"CATEGORY_ID" NUMBER, 
	"CATEGORY_NAME" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table CUSTOMERS
--------------------------------------------------------

  CREATE TABLE "COMMERCE"."CUSTOMERS" 
   (	"CUSTOMER_ID" NUMBER, 
	"CUSTOMER_USERNAME" VARCHAR2(20 BYTE), 
	"CUSTOMER_PASSWORD" VARCHAR2(20 BYTE), 
	"CUSTOMER_NAME" VARCHAR2(255 BYTE), 
	"CUSTOMER_PHONE" VARCHAR2(20 BYTE), 
	"CUSTOMER_EMAIL" VARCHAR2(50 BYTE), 
	"CUSTOMER_ADDRESS" VARCHAR2(255 BYTE), 
	"CUSTOMER_CITY" VARCHAR2(20 BYTE), 
	"CUSTOMER_STATE" VARCHAR2(20 BYTE), 
	"CUSTOMER_POSTALCODE" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table LOG
--------------------------------------------------------

  CREATE TABLE "COMMERCE"."LOG" 
   (	"LOG_ID" NUMBER, 
	"LOG_DESCRIPTION" VARCHAR2(255 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table ORDERS
--------------------------------------------------------

  CREATE TABLE "COMMERCE"."ORDERS" 
   (	"ORDER_ID" NUMBER, 
	"ORDER_PRODUCT_ID" NUMBER, 
	"ORDER_CUSTOMER_ID" NUMBER, 
	"ORDER_QUANTITY" NUMBER, 
	"ORDER_DATE" DATE, 
	"ORDER_TOTAL" NUMBER(8,2)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table PRODUCTS
--------------------------------------------------------

  CREATE TABLE "COMMERCE"."PRODUCTS" 
   (	"PRODUCT_ID" NUMBER, 
	"PRODUCT_NAME" VARCHAR2(255 BYTE), 
	"PRODUCT_CATEGORY" VARCHAR2(4000 BYTE), 
	"PRODUCT_QUANTITY" NUMBER, 
	"PRODUCT_UNIT_PRICE" NUMBER(8,2), 
	"PRODUCT_DESCRIPTION" VARCHAR2(4000 BYTE), 
	"PRODUCT_IMAGE" VARCHAR2(1000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

  CREATE TABLE "COMMERCE"."USERS" 
   (	"USER_ID" NUMBER, 
	"USER_USERNAME" VARCHAR2(50 BYTE), 
	"USER_PASSWORD" VARCHAR2(50 BYTE), 
	"USER_SCREENNAME" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Sequence CATEGORIES_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "COMMERCE"."CATEGORIES_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence CUSTOMERS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "COMMERCE"."CUSTOMERS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 81 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence LOG_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "COMMERCE"."LOG_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 581 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence ORDERS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "COMMERCE"."ORDERS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 121 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence PRODUCTS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "COMMERCE"."PRODUCTS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence USERS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "COMMERCE"."USERS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
REM INSERTING into COMMERCE.CATEGORIES
SET DEFINE OFF;
Insert into COMMERCE.CATEGORIES (CATEGORY_ID,CATEGORY_NAME) values (1,'Computers');
Insert into COMMERCE.CATEGORIES (CATEGORY_ID,CATEGORY_NAME) values (2,'Smartphones');
REM INSERTING into COMMERCE.CUSTOMERS
SET DEFINE OFF;
Insert into COMMERCE.CUSTOMERS (CUSTOMER_ID,CUSTOMER_USERNAME,CUSTOMER_PASSWORD,CUSTOMER_NAME,CUSTOMER_PHONE,CUSTOMER_EMAIL,CUSTOMER_ADDRESS,CUSTOMER_CITY,CUSTOMER_STATE,CUSTOMER_POSTALCODE) values (7,'user','1234','Kaya Cetinsu','701-553-5235423','kayacetinsu@gmail.com','LA','Los Angeles2','LE','32542');
Insert into COMMERCE.CUSTOMERS (CUSTOMER_ID,CUSTOMER_USERNAME,CUSTOMER_PASSWORD,CUSTOMER_NAME,CUSTOMER_PHONE,CUSTOMER_EMAIL,CUSTOMER_ADDRESS,CUSTOMER_CITY,CUSTOMER_STATE,CUSTOMER_POSTALCODE) values (1,'user','1234','William Hartsfield','703-555-2143','william@gmail.com','45020 Aviation Drive','Sterling','VA','20166');
Insert into COMMERCE.CUSTOMERS (CUSTOMER_ID,CUSTOMER_USERNAME,CUSTOMER_PASSWORD,CUSTOMER_NAME,CUSTOMER_PHONE,CUSTOMER_EMAIL,CUSTOMER_ADDRESS,CUSTOMER_CITY,CUSTOMER_STATE,CUSTOMER_POSTALCODE) values (6,'user','1234','George Dulley','404-555-3285','george@gmail.com','6000 North Terminal Parkway','Atlanta','GA','30320');
Insert into COMMERCE.CUSTOMERS (CUSTOMER_ID,CUSTOMER_USERNAME,CUSTOMER_PASSWORD,CUSTOMER_NAME,CUSTOMER_PHONE,CUSTOMER_EMAIL,CUSTOMER_ADDRESS,CUSTOMER_CITY,CUSTOMER_STATE,CUSTOMER_POSTALCODE) values (8,'user','1234','Edward Logan','617-555-3295','edwardl@gmail.com','1 Harborside Drive','East Boston','MA','02128');
Insert into COMMERCE.CUSTOMERS (CUSTOMER_ID,CUSTOMER_USERNAME,CUSTOMER_PASSWORD,CUSTOMER_NAME,CUSTOMER_PHONE,CUSTOMER_EMAIL,CUSTOMER_ADDRESS,CUSTOMER_CITY,CUSTOMER_STATE,CUSTOMER_POSTALCODE) values (14,'user','1234','Edward Ohare','773-555-7693','ohare@gmail.com','10000 West OHare','Chicago','IL','60666');
Insert into COMMERCE.CUSTOMERS (CUSTOMER_ID,CUSTOMER_USERNAME,CUSTOMER_PASSWORD,CUSTOMER_NAME,CUSTOMER_PHONE,CUSTOMER_EMAIL,CUSTOMER_ADDRESS,CUSTOMER_CITY,CUSTOMER_STATE,CUSTOMER_POSTALCODE) values (15,'user','1234','Fiorello LaGuardia','212-555-3923','laguardia@gmail.com','Hangar Center','Flushing','NY','11371');
Insert into COMMERCE.CUSTOMERS (CUSTOMER_ID,CUSTOMER_USERNAME,CUSTOMER_PASSWORD,CUSTOMER_NAME,CUSTOMER_PHONE,CUSTOMER_EMAIL,CUSTOMER_ADDRESS,CUSTOMER_CITY,CUSTOMER_STATE,CUSTOMER_POSTALCODE) values (16,'user','1234','Albert Lambert','314-555-4022','albert@gmail.com','10701 Lambert International Blvd.','St. Louis','MO','63145');
Insert into COMMERCE.CUSTOMERS (CUSTOMER_ID,CUSTOMER_USERNAME,CUSTOMER_PASSWORD,CUSTOMER_NAME,CUSTOMER_PHONE,CUSTOMER_EMAIL,CUSTOMER_ADDRESS,CUSTOMER_CITY,CUSTOMER_STATE,CUSTOMER_POSTALCODE) values (41,'test','test','test','1234',null,null,null,null,null);
REM INSERTING into COMMERCE.LOG
SET DEFINE OFF;
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (503,'Login the administration; ID 28FDB1D8F5AAAD4D54D8027EF9C801F2; Created: Wed Aug 16 13:13:48 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (504,'Logout the administration; ID 28FDB1D8F5AAAD4D54D8027EF9C801F2; Last Accessed: Wed Aug 16 13:19:22 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (505,'Login the administration; ID A917976E94CD6E6511B78EC1F070A6C5; Created: Wed Aug 16 13:19:45 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (506,'Logout the administration; ID A917976E94CD6E6511B78EC1F070A6C5; Last Accessed: Wed Aug 16 13:34:59 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (507,'Login the administration; ID F1B09AB120303F67F81D31B8BBB8A1FD; Created: Wed Aug 16 13:35:49 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (508,'Logout the administration; ID F1B09AB120303F67F81D31B8BBB8A1FD; Last Accessed: Wed Aug 16 13:37:41 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (509,'Login the administration; ID 8FB4CA527C95574C75414A16DEB8D38E; Created: Wed Aug 16 13:42:19 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (510,'Logout the administration; ID 8FB4CA527C95574C75414A16DEB8D38E; Last Accessed: Wed Aug 16 13:44:55 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (521,'Login the administration; ID D78150A56C9E9F814ADD142D0EC7786B; Created: Fri Aug 18 01:10:38 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (522,'Login the administration; ID E466FA7AB2F9A894A10F1EE0D622524D; Created: Fri Aug 18 01:20:18 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (523,'Login the administration; ID 3AAE215CC37EF5454817E9203B0F8D11; Created: Fri Aug 18 01:22:07 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (524,'Login the administration; ID 0432006766E144FA4823E07BFC5A08E4; Created: Fri Aug 18 01:23:51 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (525,'Login the administration; ID 9EF0D8B485933600018FC852F0CBDCA5; Created: Fri Aug 18 01:29:43 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (526,'Login the administration; ID 4473487A17D358F4848C080512A786D2; Created: Fri Aug 18 01:31:52 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (527,'Login the administration; ID E0F0646C511E327C9F177EFDF8D65CBA; Created: Fri Aug 18 01:36:49 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (528,'Login the administration; ID 80F1CDC79053AE4E4D39AAFAF2F73510; Created: Fri Aug 18 01:39:33 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (529,'Login the administration; ID EC3E4D5247967B5C923B5ECC0CEF145F; Created: Fri Aug 18 01:41:59 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (530,'Login the administration; ID 8634C011B68841E8F26E089CB67EEE47; Created: Fri Aug 18 02:04:14 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (531,'Login the administration; ID 5990BE0048179C2ED21377F01184FA8C; Created: Fri Aug 18 02:17:16 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (532,'Login the administration; ID CBFF77A077E571BEAB1694272D5D8DBE; Created: Fri Aug 18 02:20:13 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (533,'Login the administration; ID B1EB53450B095FCBC7A211DA4450292C; Created: Fri Aug 18 02:20:13 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (534,'Login the administration; ID 4480D1CD6DDC1A4FC9C1098EC7CB9F1D; Created: Fri Aug 18 02:20:13 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (535,'Login the administration; ID 2C988E517CA3D75799720281D033EAA4; Created: Fri Aug 18 02:20:13 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (536,'Login the administration; ID F337815F75A7E2AE4CA5B198F6D7825E; Created: Fri Aug 18 02:23:22 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (537,'Login the administration; ID BE537E187722F5A6A3E5C20687823ABB; Created: Fri Aug 18 02:26:08 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (538,'Login the administration; ID 7EB49777D5605D44B76061F3E1FD47C8; Created: Fri Aug 18 02:26:08 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (539,'Login the administration; ID 1881F4D5842B560F1A00E8E824CBD513; Created: Fri Aug 18 02:27:01 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (540,'Login the administration; ID 452BC07856EC45D3F274A08AB1968C49; Created: Fri Aug 18 02:27:00 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (541,'Login the administration; ID 9C8B9E5AF734DB3BECD9D9015EA9080B; Created: Fri Aug 18 02:29:02 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (542,'Login the administration; ID 69A154C6EB18810767E191225AA0C233; Created: Fri Aug 18 02:29:02 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (543,'Login the administration; ID 893E67715A51DE9B6349AF1ED0156766; Created: Fri Aug 18 02:29:01 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (544,'Login the administration; ID 8E5733A3077E3DAC6BCFAB9ADCA28D9B; Created: Fri Aug 18 02:29:45 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (545,'Login the administration; ID 4B27C1D9E9C08CBCE74BBE287490D36A; Created: Fri Aug 18 02:29:45 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (546,'Login the administration; ID 22FD11DF141E6828BEC8116FD84C9D1D; Created: Fri Aug 18 02:32:01 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (547,'Login the administration; ID B14151A094517F11915FC25D61DBF033; Created: Fri Aug 18 02:37:22 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (548,'Login the administration; ID 57AE5CAC12E2F0D8280DFE1BFF60815E; Created: Fri Aug 18 02:37:22 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (549,'Login the administration; ID B1F2592937082E4ED91CE002F9878B09; Created: Fri Aug 18 02:38:34 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (550,'Login the administration; ID 6E1461A81C12C24539024BC0F75AC72B; Created: Fri Aug 18 02:42:32 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (551,'Login the administration; ID 6B9A811516FDCAF7CFE6241D0C41C7DF; Created: Fri Aug 18 02:46:14 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (552,'Login the administration; ID C6B9B4689BF498AD0848BE3BB6DE8432; Created: Fri Aug 18 02:46:14 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (553,'Login the administration; ID 1E54A21B64D203C042E74E358D11EE9E; Created: Fri Aug 18 02:51:15 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (554,'Logout the administration; ID 1E54A21B64D203C042E74E358D11EE9E; Last Accessed: Fri Aug 18 02:52:21 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (561,'Login the administration; ID 20FA7F9298CB27B670CF4015EDE3B5F2; Created: Fri Aug 18 11:04:23 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (562,'Logout the administration; ID 20FA7F9298CB27B670CF4015EDE3B5F2; Last Accessed: Fri Aug 18 11:04:49 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (563,'Login the administration; ID 718EB1790504EEDF953079BAFC4D05E8; Created: Fri Aug 18 11:05:14 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (564,'Logout the administration; ID 718EB1790504EEDF953079BAFC4D05E8; Last Accessed: Fri Aug 18 11:08:51 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (565,'Login the administration; ID EF6523A51A3D54438B066F94BB5A2633; Created: Fri Aug 18 11:17:00 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (566,'Logout the administration; ID EF6523A51A3D54438B066F94BB5A2633; Last Accessed: Fri Aug 18 11:21:56 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (501,'Login the administration; ID 25E6C3506B50675176CE902D5EF93031; Created: Wed Aug 16 13:10:32 EDT 2017');
Insert into COMMERCE.LOG (LOG_ID,LOG_DESCRIPTION) values (502,'Logout the administration; ID 25E6C3506B50675176CE902D5EF93031; Last Accessed: Wed Aug 16 13:13:06 EDT 2017');
REM INSERTING into COMMERCE.ORDERS
SET DEFINE OFF;
Insert into COMMERCE.ORDERS (ORDER_ID,ORDER_PRODUCT_ID,ORDER_CUSTOMER_ID,ORDER_QUANTITY,ORDER_DATE,ORDER_TOTAL) values (96,6,7,1,to_date('17-08-18','RR-MM-DD'),0);
Insert into COMMERCE.ORDERS (ORDER_ID,ORDER_PRODUCT_ID,ORDER_CUSTOMER_ID,ORDER_QUANTITY,ORDER_DATE,ORDER_TOTAL) values (101,6,7,1,to_date('17-08-18','RR-MM-DD'),3);
Insert into COMMERCE.ORDERS (ORDER_ID,ORDER_PRODUCT_ID,ORDER_CUSTOMER_ID,ORDER_QUANTITY,ORDER_DATE,ORDER_TOTAL) values (102,8,7,1,to_date('17-08-18','RR-MM-DD'),2);
REM INSERTING into COMMERCE.PRODUCTS
SET DEFINE OFF;
Insert into COMMERCE.PRODUCTS (PRODUCT_ID,PRODUCT_NAME,PRODUCT_CATEGORY,PRODUCT_QUANTITY,PRODUCT_UNIT_PRICE,PRODUCT_DESCRIPTION,PRODUCT_IMAGE) values (6,'Apple Mac MC516LL','Computers',11,1500,'some information','computer1.png');
Insert into COMMERCE.PRODUCTS (PRODUCT_ID,PRODUCT_NAME,PRODUCT_CATEGORY,PRODUCT_QUANTITY,PRODUCT_UNIT_PRICE,PRODUCT_DESCRIPTION,PRODUCT_IMAGE) values (8,'ASUS Chromebook C201 11.6-Inch Laptop','Computers',49,1200,'some information','computer2.png');
Insert into COMMERCE.PRODUCTS (PRODUCT_ID,PRODUCT_NAME,PRODUCT_CATEGORY,PRODUCT_QUANTITY,PRODUCT_UNIT_PRICE,PRODUCT_DESCRIPTION,PRODUCT_IMAGE) values (9,'ASUS F555LA 15.6 Full-HD Laptop','Computers',30,2300,'some information','computer3.png');
Insert into COMMERCE.PRODUCTS (PRODUCT_ID,PRODUCT_NAME,PRODUCT_CATEGORY,PRODUCT_QUANTITY,PRODUCT_UNIT_PRICE,PRODUCT_DESCRIPTION,PRODUCT_IMAGE) values (10,'HTC Desire 550 LTE','Smartphones',200,800,'some information','mobile1.jpg');
Insert into COMMERCE.PRODUCTS (PRODUCT_ID,PRODUCT_NAME,PRODUCT_CATEGORY,PRODUCT_QUANTITY,PRODUCT_UNIT_PRICE,PRODUCT_DESCRIPTION,PRODUCT_IMAGE) values (11,'Samsung Galaxy J1 Ace','Smartphones',100,550,'some information','mobile2.jpg');
Insert into COMMERCE.PRODUCTS (PRODUCT_ID,PRODUCT_NAME,PRODUCT_CATEGORY,PRODUCT_QUANTITY,PRODUCT_UNIT_PRICE,PRODUCT_DESCRIPTION,PRODUCT_IMAGE) values (12,'Sony Xperia XA Smartphone 16GB','Smartphones',80,600,'some information','mobile3.jpg');
REM INSERTING into COMMERCE.USERS
SET DEFINE OFF;
Insert into COMMERCE.USERS (USER_ID,USER_USERNAME,USER_PASSWORD,USER_SCREENNAME) values (1,'admin','admin','Administrator');
--------------------------------------------------------
--  DDL for Index PRODUCTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "COMMERCE"."PRODUCTS_PK" ON "COMMERCE"."PRODUCTS" ("PRODUCT_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index ORDERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "COMMERCE"."ORDERS_PK" ON "COMMERCE"."ORDERS" ("ORDER_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index CUSTOMERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "COMMERCE"."CUSTOMERS_PK" ON "COMMERCE"."CUSTOMERS" ("CUSTOMER_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index CATEGORIES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "COMMERCE"."CATEGORIES_PK" ON "COMMERCE"."CATEGORIES" ("CATEGORY_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Trigger CATEGORIES_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "COMMERCE"."CATEGORIES_TRG" 
BEFORE INSERT ON CATEGORIES 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.CATEGORY_ID IS NULL THEN
      SELECT CATEGORIES_SEQ.NEXTVAL INTO :NEW.CATEGORY_ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "COMMERCE"."CATEGORIES_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger CUSTOMERS_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "COMMERCE"."CUSTOMERS_TRG" 
BEFORE INSERT ON CUSTOMERS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.CUSTOMER_ID IS NULL THEN
      SELECT CUSTOMERS_SEQ.NEXTVAL INTO :NEW.CUSTOMER_ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "COMMERCE"."CUSTOMERS_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger LOG_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "COMMERCE"."LOG_TRG" 
BEFORE INSERT ON LOG 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.LOG_ID IS NULL THEN
      SELECT LOG_SEQ.NEXTVAL INTO :NEW.LOG_ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "COMMERCE"."LOG_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger ORDERS_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "COMMERCE"."ORDERS_TRG" 
BEFORE INSERT ON ORDERS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ORDER_ID IS NULL THEN
      SELECT ORDERS_SEQ.NEXTVAL INTO :NEW.ORDER_ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "COMMERCE"."ORDERS_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger PRODUCTS_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "COMMERCE"."PRODUCTS_TRG" 
BEFORE INSERT ON PRODUCTS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.PRODUCT_ID IS NULL THEN
      SELECT PRODUCTS_SEQ.NEXTVAL INTO :NEW.PRODUCT_ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "COMMERCE"."PRODUCTS_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger USERS_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "COMMERCE"."USERS_TRG" 
BEFORE INSERT ON USERS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.USER_ID IS NULL THEN
      SELECT USERS_SEQ.NEXTVAL INTO :NEW.USER_ID FROM SYS.DUAL;
    END IF;
    IF INSERTING AND :NEW.USER_SCREENNAME IS NULL THEN
      SELECT USERS_SEQ.NEXTVAL INTO :NEW.USER_SCREENNAME FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "COMMERCE"."USERS_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Synonymn COUNTRIES
--------------------------------------------------------

  CREATE OR REPLACE SYNONYM "COMMERCE"."COUNTRIES" FOR "HR"."COUNTRIES";
--------------------------------------------------------
--  DDL for Synonymn DEPARTMENTS
--------------------------------------------------------

  CREATE OR REPLACE SYNONYM "COMMERCE"."DEPARTMENTS" FOR "HR"."DEPARTMENTS";
--------------------------------------------------------
--  DDL for Synonymn EMPLOYEES
--------------------------------------------------------

  CREATE OR REPLACE SYNONYM "COMMERCE"."EMPLOYEES" FOR "HR"."EMPLOYEES";
--------------------------------------------------------
--  DDL for Synonymn JOBS
--------------------------------------------------------

  CREATE OR REPLACE SYNONYM "COMMERCE"."JOBS" FOR "HR"."JOBS";
--------------------------------------------------------
--  DDL for Synonymn JOB_HISTORY
--------------------------------------------------------

  CREATE OR REPLACE SYNONYM "COMMERCE"."JOB_HISTORY" FOR "HR"."JOB_HISTORY";
--------------------------------------------------------
--  DDL for Synonymn LOCATIONS
--------------------------------------------------------

  CREATE OR REPLACE SYNONYM "COMMERCE"."LOCATIONS" FOR "HR"."LOCATIONS";
--------------------------------------------------------
--  Constraints for Table CUSTOMERS
--------------------------------------------------------

  ALTER TABLE "COMMERCE"."CUSTOMERS" MODIFY ("CUSTOMER_PHONE" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."CUSTOMERS" ADD CONSTRAINT "CUSTOMERS_PK" PRIMARY KEY ("CUSTOMER_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "COMMERCE"."CUSTOMERS" MODIFY ("CUSTOMER_NAME" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."CUSTOMERS" MODIFY ("CUSTOMER_PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."CUSTOMERS" MODIFY ("CUSTOMER_USERNAME" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."CUSTOMERS" MODIFY ("CUSTOMER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE "COMMERCE"."USERS" MODIFY ("USER_SCREENNAME" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."USERS" MODIFY ("USER_PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."USERS" MODIFY ("USER_USERNAME" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."USERS" MODIFY ("USER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRODUCTS
--------------------------------------------------------

  ALTER TABLE "COMMERCE"."PRODUCTS" MODIFY ("PRODUCT_QUANTITY" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."PRODUCTS" MODIFY ("PRODUCT_CATEGORY" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."PRODUCTS" MODIFY ("PRODUCT_NAME" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."PRODUCTS" ADD CONSTRAINT "PRODUCTS_PK" PRIMARY KEY ("PRODUCT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "COMMERCE"."PRODUCTS" MODIFY ("PRODUCT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CATEGORIES
--------------------------------------------------------

  ALTER TABLE "COMMERCE"."CATEGORIES" MODIFY ("CATEGORY_NAME" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."CATEGORIES" ADD CONSTRAINT "CATEGORIES_PK" PRIMARY KEY ("CATEGORY_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "COMMERCE"."CATEGORIES" MODIFY ("CATEGORY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ORDERS
--------------------------------------------------------

  ALTER TABLE "COMMERCE"."ORDERS" MODIFY ("ORDER_QUANTITY" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."ORDERS" MODIFY ("ORDER_CUSTOMER_ID" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."ORDERS" ADD CONSTRAINT "ORDERS_PK" PRIMARY KEY ("ORDER_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "COMMERCE"."ORDERS" MODIFY ("ORDER_PRODUCT_ID" NOT NULL ENABLE);
  ALTER TABLE "COMMERCE"."ORDERS" MODIFY ("ORDER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOG
--------------------------------------------------------

  ALTER TABLE "COMMERCE"."LOG" MODIFY ("LOG_ID" NOT NULL ENABLE);
