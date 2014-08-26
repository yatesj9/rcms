# RCMS API
### RCMS Routes

	All request require a valid 'client-api-token' in the header.

	** Document upload **
	
	POST 	/upload 			        - Upload Document
	POST 	/multi-upload               - Upload multiple Documents
	
	** Folders **
	
	GET 	/folders			        - Get folder list
	POST    /folders			        - Add new folder
	PUT 	/folders/{folder}           - Update folder 
	DELETE  /folders/{folder}           - Delete folder
	
	** Files ***
	
	GET     /files                      - Get all files
	GET     /files/{folder}             - Get all files from folder
	GET     /files/{folder}/{filename}  - Get specific file from folder
	DELETE  /files/{folder}/{filename}  - Delete specific file from folder
	
	** Links **
	
	GET     /links                      - Get all links
	POST    /links                      - Add new link
	PUT     /links/{name}               - Update link
	DELETE  /links/{name}               - Delete link

### Authentication Routes - Vault-API

	POST 	/tokens 			        - Authenticate Employee	
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
		401 Unauthorized {}
		
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
		401 Unauthorized {}

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
[]
```

## Response
    //Valid response
    
    //Invalid - missing client-api-token
    
---
### POST /folders
## Request
```clojure
{:headers {'Content-Type: application/json'
	       'Accept: application/json'
		   'client-api-token: GUID'}
 :body {:folder 'folderName'}
```

## Resource
    null

## Response
    //Valid response
        201 Created {:headers {'Content-Type: application/json'}}
    
    //Invalid - missing client-api-token
---
### PUT /folders/{folder}
## Request
```clojure
{:headers {'Content-Type: application/json'
	       'Accept: application/json'
		   'client-api-token: GUID'}
 :body {:folder 'folderName'}
```

## Resource
    null

## Response
    //Valid response
        201 Created {:headers {'Content-Type: application/json'}}
    
    //Invalid - missing client-api-token
---
### DELETE /folders/{folder}
## Request
```clojure
{:headers {'Content-Type: application/json'
	       'Accept: application/json'
		   'client-api-token: GUID'}
 :body {:folder 'folderName'}
```

## Resource
    null

## Response
    //Valid response
        201 Created {:headers {'Content-Type: application/json'}}
    
    //Invalid - missing client-api-token