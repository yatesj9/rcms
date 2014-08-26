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
	
	** Files **
	
	GET     /files                      - Get all files
	GET     /files/{folder}             - Get all files from folder
	GET     /files/{folder}/{filename}  - Get specific file from folder
	DELETE  /files/{folder}/{filename}  - Delete specific file from folder
	
	** Links **
	
	GET     /links                      - Get all links
	POST    /links                      - Add new link
	PUT     /links/{name}               - Update link
	DELETE  /links/{name}               - Delete link
	
	** Tags **
	
	GET     /tags                       - Get all tags
	POST    /tags                       - Add new tag
	PUT     /tags/{tag}                 - Update tag
	DELETE  /tags/{tag}                 - Delete tag

### Authentication Routes - Vault-API

	POST 	/tokens 			        - Authenticate Employee	
	
* [Routes Details](https://github.com/yatesj9/rcms/blob/master/doc/ROUTES.md)


