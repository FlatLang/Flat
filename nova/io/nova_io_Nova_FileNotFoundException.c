#include <precompiled.h>
#include <nova/io/nova_io_Nova_FileNotFoundException.h>



nova_io_Extension_VTable_FileNotFoundException nova_io_Extension_VTable_FileNotFoundException_val =
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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void nova_io_Nova_FileNotFoundException_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_io_Nova_FileNotFoundException* nova_io_Nova_FileNotFoundException_Nova_construct(nova_io_Nova_FileNotFoundException* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_io_Nova_FileNotFoundException_Nova_file)
{
	CCLASS_NEW(nova_io_Nova_FileNotFoundException, this,);
	this->vtable = &nova_io_Extension_VTable_FileNotFoundException_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super((nova_exception_Nova_Exception*)this, exceptionData);
	nova_io_Nova_FileNotFoundException_0_Nova_super(this, exceptionData);
	
	{
		nova_io_Nova_FileNotFoundException_Nova_this(this, exceptionData, nova_io_Nova_FileNotFoundException_Nova_file);
	}
	
	return this;
}

void nova_io_Nova_FileNotFoundException_Nova_destroy(nova_io_Nova_FileNotFoundException** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_io_Nova_FileNotFoundException_Nova_this(nova_io_Nova_FileNotFoundException* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_io_Nova_FileNotFoundException_Nova_file)
{
	nova_exception_Nova_Exception_4_Nova_this((nova_exception_Nova_Exception*)(this), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("File at location '")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_io_Nova_FileNotFoundException_Nova_file->nova_io_Nova_File_Nova_location), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("' does not exist")))));
}

void nova_io_Nova_FileNotFoundException_0_Nova_super(nova_io_Nova_FileNotFoundException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

