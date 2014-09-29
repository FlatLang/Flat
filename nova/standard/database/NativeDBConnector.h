#ifndef NATIVE_DBCONNECTOR
#define NATIVE_DBCONNECTOR

#include <stdio.h>
#include <nova_mysql/mysql.h>

void nova_db_connect1(char* host, char* user, char* password);
void nova_db_connect2(char* host, char* user, char* password, char* database);
void nova_db_connect3(char* host, char* user, char* password, char* database, int port, char* unix_socket, unsigned long/*Nova int*/ client_flag);
void nova_db_close();
void nova_db_select_db(char* database);
void nova_user_select(char* username, char* password, char* database);
char* nova_db_error();
void nova_exec_query(char* command);
char*** nova_get_results();
long nova_num_rows();
long nova_num_cols();

#endif
