#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_Nova_FileNotFoundException.h>

nova_standard_io_Extension_VTable_FileNotFoundException nova_standard_io_Extension_VTable_FileNotFoundException_val =
{
	{
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
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_io_Nova_FileNotFoundExceptionNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_io_Nova_FileNotFoundException* nova_standard_io_Nova_FileNotFoundException_Nova_construct(nova_standard_io_Nova_FileNotFoundException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* nova_standard_io_Nova_FileNotFoundException_Nova_file)
{
	CCLASS_NEW(nova_standard_io_Nova_FileNotFoundException, this,);
	this->vtable = &nova_standard_io_Extension_VTable_FileNotFoundException_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_exception_Nova_Exception_Nova_super((nova_standard_exception_Nova_Exception*)this, exceptionData);
	nova_standard_io_Nova_FileNotFoundException_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_io_Nova_FileNotFoundException_Nova_this(this, exceptionData, nova_standard_io_Nova_FileNotFoundException_Nova_file);
	}
	
	return this;
}

void nova_standard_io_Nova_FileNotFoundException_Nova_destroy(nova_standard_io_Nova_FileNotFoundException** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_io_Nova_FileNotFoundException_Nova_this(nova_standard_io_Nova_FileNotFoundException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* nova_standard_io_Nova_FileNotFoundException_Nova_file)
{
	nova_standard_exception_Nova_Exception_4_Nova_this((nova_standard_exception_Nova_Exception*)(this), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "File at location '"), exceptionData, nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(nova_standard_io_Nova_FileNotFoundException_Nova_file->nova_standard_io_Nova_File_Nova_location), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "' does not exist"))));
}

void nova_standard_io_Nova_FileNotFoundException_0_Nova_super(nova_standard_io_Nova_FileNotFoundException* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

