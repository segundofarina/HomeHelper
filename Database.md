# Database manual 
This is a cheatsheet for managing the database on the remote server **pampero.it.itba.edu.ar**

## Accesing the server databse via ssh
1. First you need to access to pampero via ssh

    ```
    $> ssh afarina@pamero.it.itba.edu.ar
    ```

2. Then execute `psql` connecting to host `10.16.1.110` with username `paw-2018a-4`

    ```
    $> psql -h 10.16.1.110 -U paw-2018a-4
    ```

3. You will be be asked for a password which is: **zmjTo52wS**

After you these steps you should be in the database and able to execute the queries you desire.


## Accesing the server databse in your local machine via DbVisualizer

This let's you access the server database via DBVisualizer on your local machine.

### Making a tunnel between the server and your local machine

For accesing the databse you will always need to have an open tunnel between your local machine and the remote server, so the database is available on your local machine on a local port.

 For making a tunnel via ssh execute:

```
$> ssh -L 5400:10.16.1.110:5432 afarina@pampero.it.itba.edu.ar
```

Then you should be prompt for your passowrd.

You may run `ping google.com` so the tunnel does not close due to inactivity.

As this tunnnel will need to be open everytime you want to access the databse, you may want to store the script `TunnelPaw.sh` (which executres the command) on your home directory for easy access.


### Configuiring DbVisualizer
The first time you will need to configure DbVisualizer for accesing the database.
1. Open DbVisualizer and go to: `Databse > Create Database Connection`

2. Select option no Wizard

3. Fill the form with the following info:

    * **Name**: Paw Server (or the name you want)
    * **Database Type**: PostgreSQL
    * **Driver** (JDBC): PostgreSQL
    * **Database Server**: localhost
    * **Database Port**: 5400
    * **Database**: paw-2018a-4 
    * **Database userId**: paw-2018a-4
    * **Database password**: zmjTo52wS

4. If the tunnel is still open you should click on Connect and have a succesful connection

### Useful tips

* You can make DbVisulaizer make you a diagram of the tables and their refernces by double clicking on *Tables* and open the tab *Refernces*



## Backing up the databse 
For backing up the databse you need to connect via ssh to **pampero.it.itba.edu.ar** and make use of *PostgreSQL* utility `pg_dump`.

1. Connect to Pampero via ssh:

    ```
    $> ssh afarina@pampero.it.itba.edu.ar
    ```

2. Use pg_dump for making the backup (*-h* indicates the host where the databse is and *-U* the username) and redirect the output to the file with the name you want. It is convinient to name it with the date in which the backup is made:

    ```
    $> pg_dump -C -h 10.16.1.110 -U paw-2018a-4 > backup20-11-2018.sql
    ```

After these you should have the backup on your woking directory. You can download it to your local machine via SFTP.

## Downloading your backup via SFTP

For downloading your backup to your local machine you may use STP

1. Connect to pampero via sftp:

    ```
    $> sftp afarina@pampero.it.itba.edu.ar
    ```

2. Run get and the name of your file:

    ```
    $> get backup20-11-2018.sql
    ```

## Dump your backup to a clean databse on your local machine


If you have previous databse you can drop it by executing


```
$> dropdb 'paw-2018a-4'
```

1. Creating user:

    ```
    $> createuser 'paw-2018a-4'
    ```

2. Creating Database

    ```
    $> createdb -T template0 'paw-2018a-4' -O 'paw-2018a-4'
    ```

3. Giving the user a password

    ```
    $> psql postgres
    psql=# alter user "paw-2018a-4" with encrypted password 'zmjTo52wS';
    ```

4. Granting privileges on database

    ```
    psql=# grant all privileges on database <dbname> to <username> ;
    ```

5. Dumping the backup to the new database

    ```
    $> psql --set ON_ERROR_STOP=on 'paw-2018a-4' < backup20-11-2018.sql
    ```

