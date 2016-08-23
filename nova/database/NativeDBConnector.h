#ifndef NATIVE_DBCONNECTOR
#define NATIVE_DBCONNECTOR

#include <MacroLib.h>
#include <stdio.h>
#include <stdlib.h>
#include <nova_mysql/mysql.h>

//void mysql_init(MYSQL* mysql);
//void mysql_real_connect(MYSQL* mysql, char* host, char* user, char* password, char* database, int port, char* unix_socket, unsigned long/*Nova int*/ client_flag);
//void mysql_close(MYSQL* mysql);
//void mysql_error(MYSQL* mysql);
//void mysql_select_db(MYSQL* mysql, char* database);
//long mysql_affected_rows(MYSQL* mysql);
//long mysql_num_fields(MYSQL_RES* result);
//void mysql_query(MYSQL* mysql, char* query);
//void mysql_store_result(MYSQL* mysql);
//char** mysql_fetch_row(MYSQL_RES* result);
//void mysql_free_result(MYSQL_RES* result);
//void mysql_change_user(MYSQL* mysql, char* username, char* password, char* database);

#ifdef _WIN32

#endif

MYSQL* nova_db_connect1(char* host, char* user, char* password);
MYSQL* nova_db_connect2(char* host, char* user, char* password, char* database);
MYSQL* nova_db_connect3(char* host, char* user, char* password, char* database, int port, char* unix_socket, unsigned long/*Nova int*/ client_flag);
void nova_db_close(MYSQL* mysql);
char* nova_db_error(MYSQL* mysql);
void nova_db_select_db(MYSQL* mysql, char* database);
long nova_num_rows(MYSQL* mysql);
long nova_num_cols(MYSQL_RES* result);
MYSQL_RES* nova_exec_query(MYSQL* mysql, char* command);
char** nova_getRow(MYSQL_RES* result);
char*** nova_get_results(MYSQL* mysql, MYSQL_RES* result);
void nova_user_select(MYSQL* mysql, char* username, char* password, char* database);

#endif
