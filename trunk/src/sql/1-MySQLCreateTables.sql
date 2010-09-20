-- ----------------------------------------------------------------------------
-- Database creation for iBid app.
-- -----------------------------------------------------------------------------

-- Indexes for primary keys have been explicitly created. Furthermore, with
-- InnoDB tables, there must be an index where the foreign key and the
-- referenced key are listed as the FIRST columns.

-- ---------- Table for validation queries from the connection pool -----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- -----------------------------------------------------------------------------
-- Drop tables. NOTE: before dropping a table (when re-executing the script),
-- the tables having columns acting as foreign keys of the table to be dropped,
-- must be dropped first (otherwise, the corresponding checks on those tables
-- could not be done).

DROP TABLE User;
DROP TABLE Account;
DROP TABLE Product;
DROP TABLE Category;
DROP TABLE Bid;

-- ------------------------------- Account ------------------------------------

CREATE TABLE Account ( accountId BIGINT NOT NULL AUTO_INCREMENT,
    money DOUBLE PRECISION NOT NULL, version BIGINT, 
    CONSTRAINT AccountPK PRIMARY KEY(accountId) ) ENGINE = InnoDB;

CREATE INDEX AccountByAccountIdIndex ON Account (accountId);

-- ------------------------------- User ------------------------------------

CREATE TABLE User ( userId BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(30) UNIQUE NOT NULL, password VARCHAR(30) NOT NULL, 
    name VARCHAR(40), surname VARCHAR(50), email VARCHAR(50) NOT NULL, 
    accountId BIGINT, version BIGINT,
    CONSTRAINT UserPK PRIMARY KEY(userId),
    CONSTRAINT UserAccountFK FOREIGN KEY(accountId)
        REFERENCES Account (accountId),
    CONSTRAINT validEmail CHECK ( email LIKE '%@%.%' ) ) ENGINE = InnoDB;

CREATE INDEX UserByUserIdIndex ON User (userId);
CREATE INDEX UserByAccountIdIndex ON User (userId, accountId);

-- ------------------------------ Category -----------------------------------

CREATE TABLE Category ( categoryId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT CategoryPK PRIMARY KEY(categoryId) ) ENGINE = InnoDB;

CREATE INDEX CategoryByCategoryIdIndex ON Category (categoryId);

-- ------------------------------- Product ------------------------------------

CREATE TABLE Product ( productId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL, date TIMESTAMP NOT NULL,
    categoryId BIGINT NOT NULL, version BIGINT, 
    CONSTRAINT ProductPK PRIMARY KEY(productId),
    CONSTRAINT ProductCategoryFK FOREIGN KEY(categoryId)
        REFERENCES Category (categoryId),   
    CONSTRAINT validDate CHECK ( date > CURRENT_TIMESTAMP() ) ) ENGINE = InnoDB;

CREATE INDEX ProductByProductIdIndex ON Product (productId);
CREATE INDEX ProductByCategoryIdIndex ON Product (productId, categoryId);
CREATE INDEX ProductByDateIndex ON Product (productId, date);

-- ------------------------------- Bid ------------------------------------

CREATE TABLE Bid ( bidId BIGINT NOT NULL AUTO_INCREMENT,
    money DOUBLE PRECISION NOT NULL, date TIMESTAMP NOT NULL,
    accountId BIGINT NOT NULL, productId BIGINT NOT NULL, 
    CONSTRAINT BidPK PRIMARY KEY(bidId),
    CONSTRAINT BidAccountFK FOREIGN KEY(accountId)
        REFERENCES Account (accountId),   
    CONSTRAINT BidProductFK FOREIGN KEY(productId)
        REFERENCES Product (productId), 
    CONSTRAINT validDate CHECK ( date > CURRENT_TIMESTAMP() ) ) ENGINE = InnoDB;

CREATE INDEX BidByBidIdIndex ON Bid (bidId);
CREATE INDEX BidByAccountIdIndex ON Bid (bidId, accountId);
CREATE INDEX BidByProductIdIndex ON Bid (bidId, productId);
CREATE INDEX BidByDateIndex ON Bid (bidId, date);
