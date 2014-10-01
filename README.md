# RCMS API

RCMS is an API for creating a CMS using the HTTP standard GET/POST/PUT/DELETE. Client side can be any framework or completely custom. An AngularJS frontend will be added once RCMS API is closer to completion. Authorization to the API is handled through client-api-key's.

## Installation
---
Still in early stages...

resources/custom_config.clj = Development/Production configuration
* resource path
* client-api-token
* database url/username/password  

```clojure
{:resource {
    :dev-path "path to files"}
  :token {
    :dev-client-api-token "guid"}
  :database {
    :dev-url "db url"
    :dev-username "username"
    :dev-password "password"}}
```

## Usage
---
Still in early stages..

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
POST   /folders          {:name 'foldername' :folderOrder '#' :hidden 'boolean'}
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
    * Validation
* Folders
    * Hidden folders 
    * Validation
* Files
    * ~~Complete~~
* Tags
    * ~~Complete~~
* Links
    * ~~Complete~~
    
Liberator - Resource management
* ~~Complete~~

Database Connection
* ~~Test - DB~~
* ~~Development - DB~~
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
    * hidden
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
    * link_order
* Permissions *to be determined*
    * id
    * employee_id
    * section ie.Documents/Links/Announcements/Banner/Image-links
    * folders
    * admin

## Documentation
---
* [RCMS API Routes](https://github.com/yatesj9/rcms/blob/master/doc/routes.md)
* [RCMS API Routes Details](https://github.com/yatesj9/rcms/blob/master/doc/routes_details.md)