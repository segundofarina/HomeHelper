#!/bin/bash
# Tunnel con pampero a base de datos de PAW
# #######################################################
# ### Configuracion para dbVisualizer (crear nueva database en DBVisualizer)
# ### Database Type: PostgreSQL
# ### Driver (JDBC): PostgreSQL
# ### Database Server: localhost
# ### Database Port: 5400
# ### Database: PAW (o el nombre que le quieras poner)
# ### Database userId: paw-2018a-4
# ### Database password: zmjTo52wS
# #######################################################
ssh -L 5400:10.16.1.110:5432 afarina@pampero.it.itba.edu.ar