#include <precompiled.h>
#include <nova/star/nova_star_Nova_Window.h>



nova_star_Extension_VTable_Window nova_star_Extension_VTable_Window_val =
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


void nova_star_Nova_Window_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_star_Nova_Window* nova_star_Nova_Window_Nova_construct(nova_star_Nova_Window* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_star_Nova_Window, this,);
	this->vtable = &nova_star_Extension_VTable_Window_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_star_Nova_Window_Nova_super(this, exceptionData);
	
	{
		nova_star_Nova_Window_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_star_Nova_Window_Nova_destroy(nova_star_Nova_Window** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	
	nova_Nova_String_Nova_destroy(&(*this)->nova_star_Nova_Window_Nova_title, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_star_Nova_Window_Nova_create(nova_star_Nova_Window* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_star_Nova_WindowThread* l1_Nova_t = (nova_star_Nova_WindowThread*)nova_null;
	
	l1_Nova_t = nova_star_Nova_WindowThread_Nova_construct(0, exceptionData, this);
	nova_thread_Nova_Thread_Nova_start((nova_thread_Nova_Thread*)(l1_Nova_t), exceptionData);
}

void nova_star_Nova_Window_0_Nova_this(nova_star_Nova_Window* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_star_Nova_Window_Nova_super(nova_star_Nova_Window* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_star_Nova_Window_Nova_x = 0;
	this->nova_star_Nova_Window_Nova_y = 0;
	this->nova_star_Nova_Window_Nova_width = 0;
	this->nova_star_Nova_Window_Nova_height = 0;
	this->nova_star_Nova_Window_Nova_title = (nova_Nova_String*)nova_null;
}

