#include "NativeDBConnector.h"

MYSQL* nova_db_init()
{
#ifdef _WIN32
	MYSQL* mysql = NOVA_MALLOC(sizeof(MYSQL));
	
	mysql_init(mysql);

	return mysql;
#else
    return 0;
#endif
}

MYSQL* nova_db_connect1(char* host, char* user, char* password)
{
#ifdef _WIN32
	return nova_db_connect2(host, user, password, 0);
#else
    return 0;
#endif
}

MYSQL* nova_db_connect2(char* host, char* user, char* password, char* database)
{
#ifdef _WIN32
	return nova_db_connect3(host, user, password, database, 0, NULL, 0);
#else
    return 0;
#endif
}

MYSQL* nova_db_connect3(char* host, char* user, char* password, char* database, int port, char* unix_socket, unsigned long/*Nova int*/ client_flag)
{
#ifdef _WIN32
	MYSQL* mysql = nova_db_init();

	mysql_real_connect(mysql, host, user, password, database, port, unix_socket, client_flag);
	
	return mysql;
#else
    return 0;
#endif
}

void nova_db_close(MYSQL* mysql)
{
#ifdef _WIN32
	mysql_close(mysql);
#else
#endif
}

char* nova_db_error(MYSQL* mysql)
{
#ifdef _WIN32
	return (char*)mysql_error(mysql);
#else
    return 0;
#endif
}

void nova_db_select_db(MYSQL* mysql, char* database)
{
#ifdef _WIN32
	mysql_select_db(mysql, database);
#else
#endif
}

long nova_num_rows(MYSQL* mysql)
{
#ifdef _WIN32
	return (long)mysql_affected_rows(mysql);
#else
    return 0;
#endif
}

long nova_num_cols(MYSQL_RES* result)
{
#ifdef _WIN32
	return (long)mysql_num_fields(result);
#else
    return 0;
#endif
}

MYSQL_RES* nova_exec_query(MYSQL* mysql, char* command)
{
#ifdef _WIN32
	if (mysql_query(mysql, command))
	{
		return 0;
	}

	return mysql_store_result(mysql);
#else
    return 0;
#endif
}

char** nova_getRow(MYSQL_RES* result)
{
#ifdef _WIN32
	return mysql_fetch_row(result);
#else
    return 0;
#endif
}

char*** nova_get_results(MYSQL* mysql, MYSQL_RES* result)
{
#ifdef _WIN32
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
#else
    return 0;
#endif
}

void nova_user_select(MYSQL* mysql, char* username, char* password, char* database)
{
#ifdef _WIN32
	mysql_change_user(mysql, username, password, database);
#else
#endif
}