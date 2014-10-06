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
	DELETE  /links/{name}               - Delete link
	
	** Tags **
	
	GET     /tags                       - Get all tags
	GET     /tags/{folder}              - Get all tags from folder
	POST    /tags                       - Add new tag
	DELETE  /tags/{folder}/{tag}        - Delete tag

    ** Announcements **
    
    GET     /announcements              - Get all announcements
    GET     /announcements/valid        - Get all non-expired announcements
    POST    /announcements              - Add new announcements
    DELETE  /announcements/{title}      - Delete announcements
### Authentication Routes - Vault-API

	POST 	/tokens 			        - Authenticate Employee	
	
* [RCMS API Routes Details](https://github.com/yatesj9/rcms/blob/master/doc/routes_details.md)


