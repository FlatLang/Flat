#include <precompiled.h>
#include "NativeDBConnector.h"

MYSQL* nova_db_init()
{
	MYSQL* mysql = NOVA_MALLOC(sizeof(MYSQL));
	
	mysql_init(mysql);

	return mysql;
}

MYSQL* nova_db_connect1(char* host, char* user, char* password)
{
	return nova_db_connect2(host, user, password, 0);
}

MYSQL* nova_db_connect2(char* host, char* user, char* password, char* database)
{
	return nova_db_connect3(host, user, password, database, 0, NULL, 0);
}

MYSQL* nova_db_connect3(char* host, char* user, char* password, char* database, int port, char* unix_socket, unsigned long/*Nova int*/ client_flag)
{
	MYSQL* mysql = nova_db_init();

	mysql_real_connect(mysql, host, user, password, database, port, unix_socket, client_flag);
	
	return mysql;
}

void nova_db_close(MYSQL* mysql)
{
	mysql_close(mysql);
}

char* nova_db_error(MYSQL* mysql)
{
	return (char*)mysql_error(mysql);
}

void nova_db_select_db(MYSQL* mysql, char* database)
{
	mysql_select_db(mysql, database);
}

long nova_num_rows(MYSQL* mysql)
{
	return (long)mysql_affected_rows(mysql);
}

long nova_num_cols(MYSQL_RES* result)
{
	return (long)mysql_num_fields(result);
}

MYSQL_RES* nova_exec_query(MYSQL* mysql, char* command)
{
	if (mysql_query(mysql, command))
	{
		return 0;
	}

	return mysql_store_result(mysql);
}

char** nova_getRow(MYSQL_RES* result)
{
	return mysql_fetch_row(result);
}

char*** nova_get_results(MYSQL* mysql, MYSQL_RES* result)
{
	if (result)
	{
		long numRows = nova_num_rows(mysql);
		long numCols = nova_num_cols(result);

		long    id   = 0;
		char*** rows = malloc(sizeof(char[numRows][numCols]));
		char**  row;

		while ((row = nova_getRow(result)))
		{
			rows[id++] = row;
		}

		return rows;
	}

	mysql_free_result(result);

	return 0;
}

void nova_user_select(MYSQL* mysql, char* username, char* password, char* database)
{
	mysql_change_user(mysql, username, password, database);
}
