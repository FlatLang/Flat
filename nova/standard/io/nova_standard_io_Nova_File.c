#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_Nova_File.h>

nova_standard_io_Extension_VTable_File nova_standard_io_Extension_VTable_File_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	FILE* nova_standard_io_Nova_File_Nova_fp;
	nova_standard_Nova_String* nova_standard_io_Nova_File_Nova_location;
	
)


int nova_standard_io_Nova_File_Mutator_Nova_maxOpenFiles(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_io_Nova_File_Nova_value);
void nova_standard_io_Nova_FileNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
	{
		nova_standard_io_Nova_File* l1_Nova_exitLog;
		
		l1_Nova_exitLog = nova_standard_io_Nova_File_3_Nova_construct(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "log"), exceptionData, nova_standard_primitive_number_Nova_Long_1_Nova_toString(0, exceptionData, nova_standard_time_Nova_Time_Nova_currentTimeMillis(0, exceptionData))));
	}
}

nova_standard_io_Nova_File* nova_standard_io_Nova_File_3_Nova_construct(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_io_Nova_File_Nova_location)
{
	CCLASS_NEW(nova_standard_io_Nova_File, this);
	this->vtable = &nova_standard_io_Extension_VTable_File_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_io_Nova_File_Nova_super(this, exceptionData);
	
	{
		nova_standard_io_Nova_File_3_Nova_this(this, exceptionData, nova_standard_io_Nova_File_Nova_location);
	}
	
	return this;
}

nova_standard_io_Nova_File* nova_standard_io_Nova_File_4_Nova_construct(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData, FILE* nova_standard_io_Nova_File_Nova_fp)
{
	CCLASS_NEW(nova_standard_io_Nova_File, this);
	this->vtable = &nova_standard_io_Extension_VTable_File_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_io_Nova_File_Nova_super(this, exceptionData);
	
	{
		nova_standard_io_Nova_File_4_Nova_this(this, exceptionData, nova_standard_io_Nova_File_Nova_fp);
	}
	
	return this;
}

void nova_standard_io_Nova_File_Nova_destroy(nova_standard_io_Nova_File** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_standard_Nova_String_Nova_destroy(&(*this)->prv->nova_standard_io_Nova_File_Nova_location, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_io_Nova_File_3_Nova_this(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_io_Nova_File_Nova_location)
{
	this->prv->nova_standard_io_Nova_File_Nova_location = nova_standard_io_Nova_File_Nova_location;
	this->prv->nova_standard_io_Nova_File_Nova_fp = fopen((char*)(nova_standard_io_Nova_File_Nova_location->nova_standard_Nova_String_Nova_chars), (char*)("r+"));
}

void nova_standard_io_Nova_File_4_Nova_this(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData, FILE* nova_standard_io_Nova_File_Nova_fp)
{
	this->prv->nova_standard_io_Nova_File_Nova_fp = nova_standard_io_Nova_File_Nova_fp;
}

char nova_standard_io_Nova_File_Nova_delete(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_io_Nova_File_Nova_close(this, exceptionData);
	return remove((char*)(this->prv->nova_standard_io_Nova_File_Nova_location->nova_standard_Nova_String_Nova_chars)) == 0;
}

void nova_standard_io_Nova_File_Nova_reopen(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_io_Nova_File_Nova_close(this, exceptionData);
	this->prv->nova_standard_io_Nova_File_Nova_fp = fopen((char*)(this->prv->nova_standard_io_Nova_File_Nova_location->nova_standard_Nova_String_Nova_chars), (char*)("r+"));
}

void nova_standard_io_Nova_File_Nova_rewind(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	rewind(this->prv->nova_standard_io_Nova_File_Nova_fp);
}

void nova_standard_io_Nova_File_Nova_clearContents(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (nova_standard_io_Nova_File_Accessor_Nova_exists(this, exceptionData))
	{
		this->prv->nova_standard_io_Nova_File_Nova_fp = fopen((char*)(this->prv->nova_standard_io_Nova_File_Nova_location->nova_standard_Nova_String_Nova_chars), (char*)("w"));
	}
}

char nova_standard_io_Nova_File_Nova_create(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!nova_standard_io_Nova_File_Accessor_Nova_exists(this, exceptionData))
	{
		this->prv->nova_standard_io_Nova_File_Nova_fp = fopen((char*)(this->prv->nova_standard_io_Nova_File_Nova_location->nova_standard_Nova_String_Nova_chars), (char*)("w"));
		if (!nova_standard_io_Nova_File_Accessor_Nova_exists(this, exceptionData))
		{
			perror((char*)("fopen"));
			return 0;
		}
		nova_standard_io_Nova_File_Nova_reopen(this, exceptionData);
		if (!nova_standard_io_Nova_File_Accessor_Nova_exists(this, exceptionData))
		{
			perror((char*)("fopen"));
			return 0;
		}
		return 1;
	}
	return 0;
}

nova_standard_Nova_String* nova_standard_io_Nova_File_Nova_readAllContents(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_data;
	nova_standard_Nova_String* l1_Nova_line;
	
	l1_Nova_data = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "");
	l1_Nova_line = nova_standard_io_Nova_File_Nova_readLine(this, exceptionData);
	while (l1_Nova_line != (nova_standard_Nova_String*)nova_null)
	{
		if (l1_Nova_data->nova_standard_Nova_String_Nova_size > 0)
		{
			l1_Nova_data = l1_Nova_data->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_data, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "\n"));
		}
		l1_Nova_data = l1_Nova_data->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_data, exceptionData, l1_Nova_line);
		l1_Nova_line = nova_standard_io_Nova_File_Nova_readLine(this, exceptionData);
	}
	return l1_Nova_data;
}

nova_standard_Nova_String* nova_standard_io_Nova_File_Nova_readLine(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_buf;
	int l1_Nova_size;
	char* l1_Nova_line;
	char l1_Nova_c;
	int l1_Nova_index;
	
	l1_Nova_buf = 5;
	l1_Nova_size = l1_Nova_buf;
	l1_Nova_line = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * l1_Nova_size);
	l1_Nova_c = (char)(getc(this->prv->nova_standard_io_Nova_File_Nova_fp));
	if (l1_Nova_c == EOF)
	{
		return (nova_standard_Nova_String*)nova_null;
	}
	l1_Nova_index = 0;
	while (l1_Nova_c != '\n' && l1_Nova_c != EOF)
	{
		if (l1_Nova_index >= l1_Nova_size)
		{
			l1_Nova_size = l1_Nova_size + l1_Nova_buf;
			l1_Nova_line = (char*)(realloc((char*)(l1_Nova_line), (int)(l1_Nova_size)));
		}
		l1_Nova_line[l1_Nova_index++] = l1_Nova_c;
		l1_Nova_c = (char)(getc(this->prv->nova_standard_io_Nova_File_Nova_fp));
	}
	if (l1_Nova_index >= l1_Nova_size)
	{
		l1_Nova_size++;
		l1_Nova_line = (char*)(realloc((char*)(l1_Nova_line), (int)(l1_Nova_size)));
	}
	l1_Nova_line[l1_Nova_index++] = '\0';
	l1_Nova_line = (char*)(realloc((char*)(l1_Nova_line), (int)(l1_Nova_index)));
	return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_line);
}

void nova_standard_io_Nova_File_Nova_writeLine(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_io_Nova_File_Nova_line)
{
	nova_standard_io_Nova_File_Nova_write(this, exceptionData, nova_standard_io_Nova_File_Nova_line->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_standard_io_Nova_File_Nova_line, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "\n")));
}

void nova_standard_io_Nova_File_Nova_write(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_io_Nova_File_Nova_data)
{
	fputs((char*)(nova_standard_io_Nova_File_Nova_data->nova_standard_Nova_String_Nova_chars), this->prv->nova_standard_io_Nova_File_Nova_fp);
	nova_standard_io_Nova_File_Nova_flush(this, exceptionData);
}

void nova_standard_io_Nova_File_Nova_flush(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	fflush(this->prv->nova_standard_io_Nova_File_Nova_fp);
}

void nova_standard_io_Nova_File_Nova_close(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (nova_standard_io_Nova_File_Accessor_Nova_exists(this, exceptionData))
	{
		fclose(this->prv->nova_standard_io_Nova_File_Nova_fp);
	}
}

char nova_standard_io_Nova_File_Accessor_Nova_exists(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_io_Nova_File_Nova_fp != 0;
}


int nova_standard_io_Nova_File_Accessor_Nova_maxOpenFiles(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (int)getMaxOpenFiles();
}

int nova_standard_io_Nova_File_Mutator_Nova_maxOpenFiles(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_io_Nova_File_Nova_value)
{
	short l1_Nova_min;
	short l1_Nova_max;
	
	l1_Nova_min = (short)20;
	l1_Nova_max = (short)2048;
	if (nova_standard_io_Nova_File_Nova_value > l1_Nova_max || nova_standard_io_Nova_File_Nova_value < l1_Nova_min)
	{
		nova_standard_Nova_String* nova_local_0;
		nova_standard_Nova_String* nova_local_1;
		
		nova_local_0 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, nova_standard_io_Nova_File_Nova_value);
		nova_local_1 = nova_standard_primitive_number_Nova_Short_1_Nova_toString(0, exceptionData, l1_Nova_min);
		nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Invalid max number of open files: "), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "\nValid values include "), exceptionData, nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_1, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "-"), exceptionData, nova_standard_primitive_number_Nova_Short_1_Nova_toString(0, exceptionData, l1_Nova_max)))))));
	}
	else
	{
		setMaxOpenFiles((int)(nova_standard_io_Nova_File_Nova_value));
	}
}

void nova_standard_io_Nova_File_Nova_super(nova_standard_io_Nova_File* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_io_Nova_File_Nova_fp = 0;
	this->prv->nova_standard_io_Nova_File_Nova_location = (nova_standard_Nova_String*)nova_null;
}

