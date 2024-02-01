
## Connecting Database

Install MySQL, create new user

```
create user ashu@localhost identified by 'Ashu@123';
grant all privileges on *.* to ashu@localhost;
ALTER USER 'ashu'@localhost IDENTIFIED WITH mysql_native_password BY 'Ashu@123';

```
