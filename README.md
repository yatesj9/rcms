# RCMS API

RCMS is going to be an API for creating a CMS using the HTTP standard GET/POST/PUT/DELETE. Client side can be any framework or completely custom. An AngularJS frontend will be added once RCMS API is closer to completion. 

## Installation
---
Coming Soon, still in early stages

## Usage
---
Coming Soon, still in early stages
## TODO
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
        * Get list of folder's from DB
        * Validation
    * POST
        * ~~Create~~
        * Add to DB table
        * Validation
    * PUT
        * ~~Rename~~
        * Change DB table
        * Validation
    * DELETE
        * ~~Remove~~
        * Remove from DB table
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

Authentication - Vault-API, *link to follow*

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