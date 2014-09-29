#include "NativeDBConnector.h"

MYSQL mysql;

MYSQL_RES* result;

char inited = 0;

void nova_db_init()
{
	if (inited)
	{
		return;
	}

	mysql_init(&mysql);

	inited = 1;
}

void nova_db_connect1(char* host, char* user, char* password)
{
	nova_db_connect2(host, user, password, 0);
}

void nova_db_connect2(char* host, char* user, char* password, char* database)
{
	nova_db_connect3(host, user, password, database, 0, NULL, 0);
}

void nova_db_connect3(char* host, char* user, char* password, char* database, int port, char* unix_socket, unsigned long/*Nova int*/ client_flag)
{
	nova_db_init();

	mysql_real_connect(&mysql, host, user, password, database, port, unix_socket, client_flag);
}

void nova_db_close()
{
	mysql_close(&mysql);
}

char* nova_db_error()
{
	return (char*)mysql_error(&mysql);
}

void nova_db_select_db(char* database)
{
	mysql_select_db(&mysql, database);
}

long nova_num_rows()
{
	return (long)mysql_affected_rows(&mysql);
}

long nova_num_cols()
{
	return (long)mysql_num_fields(result);
}

void nova_exec_query(char* command)
{
	if (mysql_query(&mysql, command))
	{
		printf("MySQL query error: %s\n", mysql_error(&mysql));

		exit(1);
	}

	result = mysql_store_result(&mysql);
}

char** nova_getRow()
{
	return mysql_fetch_row(result);
}

char*** nova_get_results()
{
	if (result)
	{
		long numRows = nova_num_rows();
		long numCols = nova_num_cols();

		long    id   = 0;
		char*** rows = malloc(sizeof(char[numRows][numCols]));
		char**  row;

		while ((row = nova_getRow()))
		{
			rows[id++] = row;
		}

		return rows;
	}

	mysql_free_result(result);

	return 0;
}

void nova_user_select(char* username, char* password, char* database)
{
	mysql_change_user(&mysql, username, password, database);
}
