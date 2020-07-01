# file-handler
Rest end point to upload file with file name 
Rest end point to retrieve file with file name

Assumtions:
Only valid filenames are provided. With the implementation of validation this assumtions can be removed.
No file size validation as of now.  A validation for size check is required.


TODO:
Externalise: Message strings
REfractor code to introduce FileService - contains the details of directory and file handling whereas controller will be responsible for handling the request only.