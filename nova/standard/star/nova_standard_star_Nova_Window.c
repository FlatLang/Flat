#include <precompiled.h>
#include <nova/standard/star/nova_standard_star_Nova_Window.h>

nova_standard_star_Extension_VTable_Window nova_standard_star_Extension_VTable_Window_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_star_Nova_WindowNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_star_Nova_Window* nova_standard_star_Nova_Window_Nova_Window(nova_standard_star_Nova_Window* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_star_Nova_Window, this,);
	this->vtable = &nova_standard_star_Extension_VTable_Window_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_star_Nova_Window_Nova_super(this, exceptionData);
	
	{
		nova_standard_star_Nova_Window_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_star_Nova_Window_Nova_destroy(nova_standard_star_Nova_Window** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	
	nova_standard_Nova_String_Nova_destroy(&(*this)->nova_standard_star_Nova_Window_Nova_title, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_star_Nova_Window_Nova_create(nova_standard_star_Nova_Window* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_star_Nova_WindowThread* l1_Nova_t;
	
	l1_Nova_t = nova_standard_star_Nova_WindowThread_Nova_WindowThread(0, exceptionData, this);
	nova_standard_thread_Nova_Thread_Nova_start((nova_standard_thread_Nova_Thread*)(l1_Nova_t), exceptionData);
}

void nova_standard_star_Nova_Window_0_Nova_this(nova_standard_star_Nova_Window* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_star_Nova_Window_Nova_super(nova_standard_star_Nova_Window* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_star_Nova_Window_Nova_x = 0;
	this->nova_standard_star_Nova_Window_Nova_y = 0;
	this->nova_standard_star_Nova_Window_Nova_width = 0;
	this->nova_standard_star_Nova_Window_Nova_height = 0;
	this->nova_standard_star_Nova_Window_Nova_title = (nova_standard_Nova_String*)nova_null;
}

