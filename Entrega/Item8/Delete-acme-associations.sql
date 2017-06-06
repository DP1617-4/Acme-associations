

start transaction;


use `acme-associations`;

revoke all privileges on `acme-associations`.* from 'acme-user'@'%';

revoke all privileges on `acme-associations`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';

drop user 'acme-manager'@'%';

drop database `acme-associations`;

commit;
