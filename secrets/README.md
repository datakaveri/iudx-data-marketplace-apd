| Key                               | Purpose                                                                                                                             | Details                                                                                                                                                                                                                                                                                                    |
|-----------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| dxApiBasePath                     | API base path for DX DMP-APD server Default : /dx/apd/acl/v1                                                                        | Reference : [here](https://swagger.io/docs/specification/2-0/api-host-and-base-path/)                                                                                                                                                                                                                      |
| dxCatalogueBasePath               | API base path for DX Catalogue server Default : /iudx/cat/v1                                                                        | Reference :  [here]( https://swagger.io/docs/specification/2-0/api-host-and-base-path/ )                                                                                                                                                                                                                   |
| dxAuthBasePath                    | API base path for DX AAA server Default : /auth/v1                                                                                  | Reference :  [here]( https://swagger.io/docs/specification/2-0/api-host-and-base-path/ )                                                                                                                                                                                                                   |
| catServerPort                     | Host name of DX Catalogue server for fetching the information of resources, resource groups                                         | In order to connect to the DX Catalogue server, required information such as catServerHost, catServerPort, dxCatalogueBasePath etc., should be updated in commonConfig module                                                                                                                              |
| catServerHost                     | Port number to access HTTPS APIs of Catalogue Server   Default : 443                                                                | In order to connect to the DX Catalogue server, required information such as catServerHost, catServerPort, dxCatalogueBasePath etc., should be updated in commonConfig module                                                                                                                              |
| catItemPath                       | DX Catalogue Item endpoint path : [Link to API](https://api.cat-test.iudx.io/apis#tag/Entity/operation/get%20item)                  | In order to connect to the DX Catalogue server, required information such as catServerHost, catServerPort, dxCatalogueBasePath etc., should be updated in commonConfig module                                                                                                                              |
| catRelPath                        | DX Catalogue Relationship path : [Link to API](https://api.cat-test.iudx.io/apis#tag/Relationship/operation/get%20related%20entity) | In order to connect to the DX Catalogue server, required information such as catServerHost, catServerPort, dxCatalogueBasePath etc., should be updated in commonConfig module                                                                                                                              |
| authPort                          | Port number to access HTTPS APIs of Auth server Default : 443                                                                       | In order to connect with DX Authentication server, required information like authServerHost, authPort, dxAuthBasePath etc., should be updated in commonConfig, AuthenticationVerticle module                                                                                                               |
| authHost                          | Host name of Auth Server for fetching information about user, delegate, authentication of user                                      | In order to connect with DX Authentication server, required information like authServerHost, authPort, dxAuthBasePath etc., should be updated in commonConfig, AuthenticationVerticle module                                                                                                               |
| tables                            | Tables listed in postgres for DX DMP APD server                                                                                     |                                                                                                                                                                                                                                                                                                            |
| clientId                          | APD trustee client ID                                                                                                               | DX DMP-APD as trustee user credentials                                                                                                                                                                                                                                                                     |
| clientSecret                      | APD trustee client secret                                                                                                           | DX DMP-APD as trustee user credentials                                                                                                                                                                                                                                                                     |
| enableLogging                     | To enable Razorpay logs                                                                                                             | Set as false in production instance of DX DMP APD Server                                                                                                                                                                                                                                                   |
| id                                | Address of the verticle                                                                                                             | Example : iudx.apd.acl.server.apiserver.ApiServerVerticle , iudx.apd.acl.server.policy.PolicyVerticle                                                                                                                                                                                                      |
| isWorkerVerticle                  | Checks if worker verticles are to be created for a given verticle to perform blocking operations                                    |                                                                                                                                                                                                                                                                                                            |
| verticleInstances                 | Number of instances required for verticles                                                                                          |                                                                                                                                                                                                                                                                                                            |
| ssl                               | To create a encrypted link between the browser and server to keep the information private and secure                                |                                                                                                                                                                                                                                                                                                            |
| httpPort                          | Port for running the instance DX DMP-APD Server                                                                                     |                                                                                                                                                                                                                                                                                                            |
| ssl                               | To create a encrypted link between the browser and server to keep the information private and secure                                |                                                                                                                                                                                                                                                                                                            |
| authServerHost                    | Host name of Auth Server for fetching information about user, delegate, authentication of user                                      |                                                                                                                                                                                                                                                                                                            |
| jwtIgnoreExpiry                   | Set to while using the server locally to allow expired tokens                                                                       | Set as false in Production and Development instance of DX DMP-APD server                                                                                                                                                                                                                                   |
| issuer                            | To authenticate the issuer in the token                                                                                             |                                                                                                                                                                                                                                                                                                            |
| apdURL                            | DX DMP-APD URL to validate audience field                                                                                           |                                                                                                                                                                                                                                                                                                            |
| databaseIP                        | Database IP address                                                                                                                 | In order to connect to the appropriate Postgres database, required information like databaseIP, databasePort, etc., could be updated in commonConfig module                                                                                                                                                |
| databasePort                      | Port number                                                                                                                         | In order to connect to the appropriate Postgres database, required information like databaseIP, databasePort, etc., could be updated in commonConfig module                                                                                                                                                |
| databaseName                      | Database name                                                                                                                       | In order to connect to the appropriate Postgres database, required information like databaseIP, databasePort, etc., could be updated in commonConfig module                                                                                                                                                |
| databaseUserName                  | Database user name                                                                                                                  | In order to connect to the appropriate Postgres database, required information like databaseIP, databasePort, etc., could be updated in commonConfig module                                                                                                                                                |
| databasePassword                  | Database password                                                                                                                   | In order to connect to the appropriate Postgres database, required information like databaseIP, databasePort, etc., could be updated in commonConfig module                                                                                                                                                |
| poolSize                          | Pg pool size for Postgres Client Reference :  https://vertx.io/docs/vertx-pg-client/java/                                           | In order to connect to the appropriate Postgres database, required information like databaseIP, databasePort, etc., could be updated in commonConfig module                                                                                                                                                |
| isAccountActivationCheckBeingDone | Enabled to true if account activation check from Razorpay is being done                                                             | Set as true in Production and Development instance of DX DMP-APD server                                                                                                                                                                                                                                    |
| dataBrokerIP                      | RMQ IP address                                                                                                                      | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server](https://github.com/datakaveri/auditing-server)   |
| dataBrokerPort                    | RMQ port number                                                                                                                     | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| dataBrokerVhost                   | Vhost being used to send Audit information Default : IUDX-INTERNAL                                                                  | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| dataBrokerUserName                | User name for RMQ                                                                                                                   | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| dataBrokerPassword                | Password for RMQ                                                                                                                    | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| dataBrokerManagementPort          |                                                                                                                                     | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| connectionTimeout                 | Setting connection timeout as part of RabbitMQ config options to set up webclient                                                   | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| requestedHeartbeat                | Defines after what period of time the peer TCP connection should be considered unreachable by RabbitMQ                              | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| handshakeTimeout                  | To increase or decrease the default connection time out                                                                             | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| requestedChannelMax               | Tells no more that 5 (or given number) could be opened up on a connection at the same time                                          | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| networkRecoveryInterval           | Interval to restart the connection between rabbitmq node and clients                                                                | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| automaticRecoveryEnabled          |                                                                                                                                     | For sending Audit information to the auditing server. In order to connect to the appropriate RabbitMQ instance, required information such as dataBrokerIP, dataBrokerPort etc. should be updated in AuditingVerticle module. [Link to DX Auditing Server]( https://github.com/datakaveri/auditing-server ) |
| razorPayKey                       | Razorpay generated key to access Razorpay APIs                                                                                      | Razorpay configuration to connect with Razorpay for order creation, payment verification etc.,                                                                                                                                                                                                             |
| razorPaySecret                    | Razorpay generated secret to access Razorpay APIs                                                                                   | Razorpay configuration to connect with Razorpay for order creation, payment verification etc.,                                                                                                                                                                                                             |
| webhook_secret                    | Secret used while creating webhook on Razorpay dashboard                                                                            | Razorpay configuration to connect with Razorpay for order creation, payment verification etc.,                                                                                                                                                                                                             |
|                                   |                                                                                                                                     |                                                                                                                                                                                                                                                                                                            |
|                                   |                                                                                                                                     |                                                                                                                                                                                                                                                                                                            |