# RCMS API

RCMS is going to be an API for creating a CMS using the HTTP standard GET/POST/PUT/DELETE. Client side can be any framework or completely custom. An AngularJS frontend will be added once RCMS API is closer to completion. 

## Installation
---
Coming Soon, still in early stages

## Usage
---
Coming Soon, still in early stages
## To-Do/Tasks
---
Routes/Model functions
* Upload
    * ~~Single Upload~~
        * Add to DB table
        * Folder selection
        * Tag selection
        * Validation  
    * Multi Upload
        * Add to DB table   
        * Folder selection
        * Tag selection
        * Validation  
* Folders
    * GET
        * ~~Get list of folder from path~~
        * ~~Get single folder from DB~~
        * Get list of folder's from DB        
        * Validation
    * POST
        * ~~Create~~
        * ~~Add to DB table~~
            * ~~Allow only one record per folder name~~ 
        * Validation
    * PUT
        * ~~Rename~~
        * Change DB table
        * Validation
    * DELETE
        * ~~Remove~~
        * ~~Remove from DB table~~
        * Validation
* Files
    * GET
    * DELETE
* Links
    * GET
    * POST
    * PUT
    * DELETE
* Tags
    * GET
    * POST
    * PUT
    * DELETE

Liberator - Resource management
* Add
* Configure

Database Connection
* ~~Bone connection pooling~~
* ~~Create Tbl~~
* ~~Drop Tbl~~
* Test - DB
    * ~~H2 DB~~
    * ~~Initialization~~
    * ~~Schema~~
    * Fixtures
* Development - DB
* Production - DB

Authentication - Vault-API, *link to follow*

API-KEY
* Add
* Remove

Authorization - local/remote DB table
* Upload permissions
    * Folder permissions
        * Allowed users 
    * Tag permissions
        * Allowed users
    * Allowed users
* Link permissions


## Documentation
---
* [RCMS API Routes](https://github.com/yatesj9/rcms/blob/master/doc/routes.md)
* [RCMS API Routes Details](https://github.com/yatesj9/rcms/blob/master/doc/routes_details.md)