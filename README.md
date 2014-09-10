# RCMS API

RCMS is going to be an API for creating a CMS using the HTTP standard GET/POST/PUT/DELETE. Client side can be any framework or completely custom. An AngularJS frontend will be added once RCMS API is closer to completion. 

## Installation
---
Coming Soon, still in early stages

## Usage
---
Coming Soon, still in early stages

Upload
```clojure
POST   /upload       {:file {:size '##' :tempfile #<File > :filename '######'}
                      :folder "foldername"
                      :tag "tagname"}
POST   /multi-upload [{:file {:size '##' :tempfile #<File > :filename '######'}
                             {:size '##' :tempfile #<File > :filename '######'}
                       :folder "foldername"
                       :tag "tagname"}]
        NOTE: Folder and tag is applied to all files in multi-upload
```

Folders
```clojure
GET    /folders
POST   /folders          {:name 'foldername' :folderOrder '#'}
PUT    /folders/{folder} {:newName 'foldername'}
DELETE /folders/{folder} 
```

Files
```clojure
GET    /files
GET    /files/{folder}
GET    /files/{folder}/{filename}
DELETE /files/{folder}/{filename}
```

Tags
```clojure
GET    /tags
GET    /tags/{folder}
POST   /tags                {:name 'tagname' :folderName 'foldername'}
DELETE /tags/{folder}/{tag}
```

Links
```clojure
GET    /links
POST   /links        {:name 'linkname' :href 'linkhref' :linkOrder '#'}
DELETE /links/{name}
```

Authorization
```clojure
GET    /users
POST   /users/tokens {:token 'token' :id 'id'}
```

## To-Do/Tasks
---
Routes/Model functions
* Upload
    * ~~Single Upload~~
        * ~~POST~~ 
        * ~~Add to DB table~~
        * ~~Folder selection~~
        * ~~Tag selection~~
        * Validation  
    * Multi Upload
        * ~~POST~~ 
        * ~~Add to DB table~~   
        * ~~Folder selection~~
        * ~~Tag selection~~
        * Validation  
* Folders
    * GET
        * ~~Get list of folder from path~~
        * ~~Get single folder from DB~~
        * ~~Get list of folder's from DB~~
        * ~~Get single folder from DB~~
        * Validation
    * POST
        * ~~Create~~
        * ~~Add to DB table~~
            * ~~Allow only one record per folder name~~ 
        * Validation
    * PUT
        * ~~Rename~~
        * ~~Change DB table~~
        * ~~Change filesystem folder~~
        * Validation
    * DELETE
        * ~~Remove~~
        * ~~Remove from DB table~~
        * ~~Remove from filessystem~~
        * Validation
* Files
    * ~~Get single file from DB~~
    * ~~Get all files from folder in DB~~
    * ~~Save file to DB~~
    * ~~Remove file from DB~~
    * ~~GET single~~
    * ~~GET all~~
    * ~~GET all from folder~~
    * ~~DELETE~~
* Tags
    * ~~Get all tags~~
    * ~~Get all tags from folder~~
    * ~~Get specific tag name and folder~~
    * ~~Save tag to DB~~
    * GET
    	* ~~All tags~~
        * ~~Single tag matching folder~~
        * ~~All tags from folder~~
    * POST
        * ~~Add tag~~ 
    * DELETE
        * ~~Remove tag~~ 
* Links
    * ~~Get all links~~
    * ~~Save links~~
    * GET
        * ~~All links~~ 
    * POST
        * ~~Add single link~~ 
    * DELETE
        * ~~Delete link~~ 

Liberator - Resource management
* ~~Add~~

Database Connection
* ~~Bone connection pooling~~
* ~~Create Tbl~~
* ~~Drop Tbl~~
* Test - DB
    * ~~H2 DB~~
    * ~~Initialization~~
    * ~~Schema~~
    * ~~Fixtures~~
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


DB
* Folders
    * id 
    * name
    * folder_order
* Files
    * id
    * folder_name
    * file_name
    * tags
    * updated_at
    * link_order
* Tags
    * id
    * name
    * folder_name
* Links
    * id
    * name
* Permissions
    * id
    * employee_id
    * section ie.Documents/Links/Announcements/Banner/Image-links
    * folders
    * admin

## Documentation
---
* [RCMS API Routes](https://github.com/yatesj9/rcms/blob/master/doc/routes.md)
* [RCMS API Routes Details](https://github.com/yatesj9/rcms/blob/master/doc/routes_details.md)