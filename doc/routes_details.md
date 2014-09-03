---
# Document Upload
---
### POST /upload
## Request
```clojure
{:headers {'client-api-token: GUID'}
    :file ####}
```			   
## Resource
```clojure
{{:file {:size 3937, :tempfile #<File /var/folders/TEMP LOCATION>, :content-type
application/x-javascript, :filename ######}}}
```
## Response
	//Successful upload
		201 Created {:headers {'Content-Type: application/json'}}
	
	//Invalid - missing client-api-token
        401 Unauthorized {:headers {'Content-Type: text/palin'}
                          :body {"client-request-token is missing or invalid"}
		
---
# Uploading Multiple Documents
---
### POST /multi-upload
## Request
```clojure
{:headers {'client-api-token: GUID'}
    :file ####, ####}
```
	
## Resource
Vector containing map of multiple files
```clojure
[{:file {:size 3937, :tempfile #<File /var/folders/TEMP LOCATION>, :content-type
application/x-javascript, :filename ######}}
{:file {:size 3937, :tempfile #<File /var/folders/TEMP LOCATION>, :content-type
application/x-javascript, :filename ######}}]
```

## Response
    //Successful upload
		201 Created {:headers {'Content-Type: application/json'}}
	
	//Invalid - missing client-api-token
        401 Unauthorized {:headers {'Content-Type: text/palin'}
                          :body {"client-request-token is missing or invalid"}

---
# Folders
---
### GET /folders
## Request
```clojure
{:headers {'client-api-token: GUID'}}
```

## Resource
List/Vector of folders
```clojure
[VECTOR OF MAPS]
```

## Response
    //Valid response
        200 OK {:headers {'Content-Type: application/json'}
                :body [VECTOR OF MAPS]
    //Invalid - missing client-api-token
        401 Unauthorized {:headers {'Content-Type: text/palin'}
                          :body {"client-request-token is missing or invalid"}
    
---
### GET /folders/{folder}
## Request
```clojure
{:headers {'client-api-token: GUID'}}
```

## Resource
Single Map of folder
```clojure
{:id '##' :name 'foldername' :folder 'foldername'}
```

## Response
    //Valid response
        200 OK {:headers {'Content-Type: application/json'}
                :body {MAP OF FOLDER}
    //Invalid - missing client-api-token
        401 Unauthorized {:headers {'Content-Type: text/palin'}
                          :body {"client-request-token is missing or invalid"} 
---
### POST /folders
## Request
```clojure
{:headers {'Content-Type: application/json'
	       'Accept: application/json'
		   'client-api-token: GUID'}
 :body {:name "Folder Name"}
```

## Resource
    null

## Response
    //Valid response
        201 Created {:headers {'Content-Type: application/json'}}
    
    //Invalid - missing client-api-token
        401 Unauthorized {:headers {'Content-Type: text/palin'}
                          :body {"client-request-token is missing or invalid"}
---
### PUT /folders/{folder}
## Request
```clojure
{:headers {'Content-Type: application/json'
	       'Accept: application/json'
		   'client-api-token: GUID'}
 :body {:new-name "New folder name"}
```

## Resource
    null

## Response
    //Valid response
        201 Created {:headers {'Content-Type: application/json'}}
    
    //Invalid - missing client-api-token
        401 Unauthorized {:headers {'Content-Type: text/palin'}
                          :body {"client-request-token is missing or invalid"}
---
### DELETE /folders/{folder}
## Request
```clojure
{:headers {'Content-Type: application/json'
	       'Accept: application/json'
		   'client-api-token: GUID'}
```

## Resource
    null

## Response
    //Valid response
        201 Created {:headers {'Content-Type: application/json'}}
    
    //Invalid - missing client-api-token
        401 Unauthorized {:headers {'Content-Type: text/palin'}
                          :body {"client-request-token is missing or invalid"}