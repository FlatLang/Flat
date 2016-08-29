#include <precompiled.h>
#include <nova/star/nova_star_Nova_WindowThread.h>



nova_star_Extension_VTable_WindowThread nova_star_Extension_VTable_WindowThread_val =
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
	nova_star_Nova_WindowThread_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_thread_Nova_Thread_Nova_handle;
	
	nova_star_Nova_Window* nova_star_Nova_WindowThread_Nova_window;
	
)
void nova_star_Nova_WindowThread_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_star_Nova_WindowThread* nova_star_Nova_WindowThread_Nova_construct(nova_star_Nova_WindowThread* this, nova_exception_Nova_ExceptionData* exceptionData, nova_star_Nova_Window* nova_star_Nova_WindowThread_Nova_window)
{
	CCLASS_NEW(nova_star_Nova_WindowThread, this);
	this->vtable = &nova_star_Extension_VTable_WindowThread_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_thread_Nova_Thread_Nova_super((nova_thread_Nova_Thread*)this, exceptionData);
	nova_star_Nova_WindowThread_0_Nova_super(this, exceptionData);
	
	{
		nova_star_Nova_WindowThread_1_Nova_this(this, exceptionData, nova_star_Nova_WindowThread_Nova_window);
	}
	
	return this;
}

void nova_star_Nova_WindowThread_Nova_destroy(nova_star_Nova_WindowThread** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_star_Nova_Window_Nova_destroy(&(*this)->prv->nova_star_Nova_WindowThread_Nova_window, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_star_Nova_WindowThread_1_Nova_this(nova_star_Nova_WindowThread* this, nova_exception_Nova_ExceptionData* exceptionData, nova_star_Nova_Window* nova_star_Nova_WindowThread_Nova_window)
{
	this->prv->nova_star_Nova_WindowThread_Nova_window = nova_star_Nova_WindowThread_Nova_window;
}

void nova_star_Nova_WindowThread_Nova_run(nova_star_Nova_WindowThread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_createWindow((int)(this->prv->nova_star_Nova_WindowThread_Nova_window->nova_star_Nova_Window_Nova_x), (int)(this->prv->nova_star_Nova_WindowThread_Nova_window->nova_star_Nova_Window_Nova_y), (int)(this->prv->nova_star_Nova_WindowThread_Nova_window->nova_star_Nova_Window_Nova_width), (int)(this->prv->nova_star_Nova_WindowThread_Nova_window->nova_star_Nova_Window_Nova_height), (char*)(this->prv->nova_star_Nova_WindowThread_Nova_window->nova_star_Nova_Window_Nova_title->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data));
}

void nova_star_Nova_WindowThread_0_Nova_super(nova_star_Nova_WindowThread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_star_Nova_WindowThread_Nova_window = (nova_star_Nova_Window*)nova_null;
}

