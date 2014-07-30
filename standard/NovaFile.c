#include <precompiled.h>
#include "NovaFile.h"


nova_VTable_File nova_VTable_File_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};
CCLASS_PRIVATE
(
	FILE* nova_File_fp;
	String* nova_File_location;
	
)

File* nova_1_File_File(File* this, ExceptionData* exceptionData, String* nova_0_location)
{
	CCLASS_NEW(File, this);
	
	this->prv->nova_File_fp = (FILE*)0;
	this->prv->nova_File_location = (String*)0;
	this->vtable = &nova_VTable_File_val;
	{
		this->prv->nova_File_location = nova_0_location;
		this->prv->nova_File_fp = fopen(nova_String_toCharArray(nova_0_location, exceptionData), (char*)("r+"));
	}
	
	return this;
}

File* nova_2_File_File(File* this, ExceptionData* exceptionData, FILE* nova_0_fp)
{
	CCLASS_NEW(File, this);
	
	this->prv->nova_File_fp = (FILE*)0;
	this->prv->nova_File_location = (String*)0;
	this->vtable = &nova_VTable_File_val;
	{
		this->prv->nova_File_fp = nova_0_fp;
	}
	
	return this;
}

void nova_del_File(File** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_String(&(*this)->prv->nova_File_location, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

char nova_File_delete(File* this, ExceptionData* exceptionData)
{
	nova_File_close(this, exceptionData);
	return remove(nova_String_toCharArray(this->prv->nova_File_location, exceptionData)) == 0;
}

void nova_File_reopen(File* this, ExceptionData* exceptionData)
{
	nova_File_close(this, exceptionData);
	this->prv->nova_File_fp = fopen(nova_String_toCharArray(this->prv->nova_File_location, exceptionData), (char*)("r+"));
}

void nova_1_File_rewind(File* this, ExceptionData* exceptionData)
{
	rewind(this->prv->nova_File_fp);
}

char nova_File_exists(File* this, ExceptionData* exceptionData)
{
	return this->prv->nova_File_fp != 0;
}

char nova_File_create(File* this, ExceptionData* exceptionData)
{
	if (!nova_File_exists(this, exceptionData))
	{
		this->prv->nova_File_fp = fopen(nova_String_toCharArray(this->prv->nova_File_location, exceptionData), (char*)("wb"));
		if (!nova_File_exists(this, exceptionData))
		{
			perror((char*)("fopen"));
			return 0;
		}
		nova_File_reopen(this, exceptionData);
		if (!nova_File_exists(this, exceptionData))
		{
			perror((char*)("fopen"));
			return 0;
		}
		return 1;
	}
	return 0;
}

String* nova_File_readAllContents(File* this, ExceptionData* exceptionData)
{
	String* nova_1_data;
	String* nova_1_line;
	
	nova_1_data = nova_String_String(0, exceptionData, (char*)(""));
	nova_1_line = nova_File_readLine(this, exceptionData);
	while (nova_1_line != (String*)0)
	{
		if (nova_1_data->nova_String_length > 0)
		{
			nova_1_data = nova_String_concat(nova_1_data, exceptionData, nova_String_String(0, exceptionData, (char*)("\n")));
		}
		nova_1_data = nova_String_concat(nova_1_data, exceptionData, nova_1_line);
		nova_1_line = nova_File_readLine(this, exceptionData);
	}
	return nova_1_data;
}

String* nova_File_readLine(File* this, ExceptionData* exceptionData)
{
	int nova_1_buf;
	int nova_1_size;
	char* nova_1_line;
	char nova_1_c;
	int nova_1_index;
	
	nova_1_buf = 5;
	nova_1_size = nova_1_buf;
	nova_1_line = (char*)NOVA_MALLOC(sizeof(char) * (nova_1_size));
	nova_1_c = getc(this->prv->nova_File_fp);
	if (nova_1_c == EOF)
	{
		return (String*)0;
	}
	nova_1_index = 0;
	while (nova_1_c != '\n' && nova_1_c != EOF)
	{
		if (nova_1_index >= nova_1_size)
		{
			nova_1_size = nova_1_size + nova_1_buf;
			nova_1_line = realloc(nova_1_line, nova_1_size);
		}
		nova_1_line[nova_1_index++] = nova_1_c;
		nova_1_c = getc(this->prv->nova_File_fp);
	}
	if (nova_1_index >= nova_1_size)
	{
		nova_1_size++;
		nova_1_line = realloc(nova_1_line, nova_1_size);
	}
	nova_1_line[nova_1_index++] = '\0';
	nova_1_line = realloc(nova_1_line, nova_1_index);
	return nova_String_String(0, exceptionData, nova_1_line);
}

void nova_File_writeLine(File* this, ExceptionData* exceptionData, String* nova_0_line)
{
	nova_File_write(this, exceptionData, nova_String_concat(nova_0_line, exceptionData, nova_String_String(0, exceptionData, (char*)("\n"))));
}

void nova_File_write(File* this, ExceptionData* exceptionData, String* nova_0_data)
{
	fputs(nova_String_toCharArray(nova_0_data, exceptionData), this->prv->nova_File_fp);
	nova_File_flush(this, exceptionData);
}

void nova_File_flush(File* this, ExceptionData* exceptionData)
{
	fflush(this->prv->nova_File_fp);
}

void nova_File_close(File* this, ExceptionData* exceptionData)
{
	if (nova_File_exists(this, exceptionData))
	{
		fclose(this->prv->nova_File_fp);
	}
}

int nova_static_File_getMaxOpenFiles(File* this, ExceptionData* exceptionData)
{
	return getMaxOpenFiles();
}

void nova_static_File_setMaxOpenFiles(File* this, ExceptionData* exceptionData, int nova_0_max)
{
	if (nova_0_max > 2048 || nova_0_max < 20)
	{
		nova_static_1_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_String(0, exceptionData, (char*)("Invalid max number of open files: ")), exceptionData, nova_String_concat(nova_2_Integer_toString(nova_Integer_Integer(0, exceptionData, nova_0_max), exceptionData), exceptionData, nova_String_String(0, exceptionData, (char*)("\nValid values include 20-2048")))));
	}
	else
	{
		setMaxOpenFiles(nova_0_max);
	}
}
