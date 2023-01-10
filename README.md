## Practice project

A simple project that uses AWS S3 SDK to upload images, then eventually download them.\
It is Spring Boot Java application with a React frontend.

Usage
--------------
Pass the AWS S3 access key and secret key as argument, when running the application's `Main` class.\
Ask the author for these credentials. Or feel free to use your own ones :) in this case navigate to
`edu.matyas94k.s3practice.bucket.BucketEnum` and modify the bucket name to match yours, before starting up the
project.\
Then open a terminal and navigate to `src/main/s3practice-frontend`, resolve the node modules with `npm i` then run the
frontend with `npm start`.\
It will open `localhost:3000` in a browser tab that will present you the frontend of the application.\
See the `README.md` file in that directory for further information.

Acknowledgment
--------------
Project realized by following this course:\
[Java Image upload | Amigoscode](https://amigoscode.com/p/image-upload-spring-boot)
