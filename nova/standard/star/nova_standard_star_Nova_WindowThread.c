#include <precompiled.h>
#include <nova/standard/star/nova_standard_star_Nova_WindowThread.h>

nova_standard_star_Extension_VTable_WindowThread nova_standard_star_Extension_VTable_WindowThread_val =
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
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_star_Nova_WindowThread_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_Nova_Thread_Nova_handle;
	
	nova_standard_star_Nova_Window* nova_standard_star_Nova_WindowThread_Nova_window;
	
)
void nova_standard_star_Nova_WindowThreadNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_star_Nova_WindowThread* nova_standard_star_Nova_WindowThread_1_Nova_construct(nova_standard_star_Nova_WindowThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_star_Nova_Window* nova_standard_star_Nova_WindowThread_Nova_window)
{
	CCLASS_NEW(nova_standard_star_Nova_WindowThread, this);
	this->vtable = &nova_standard_star_Extension_VTable_WindowThread_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_thread_Nova_Thread_Nova_super((nova_standard_thread_Nova_Thread*)this, exceptionData);
	nova_standard_star_Nova_WindowThread_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_star_Nova_WindowThread_1_Nova_this(this, exceptionData, nova_standard_star_Nova_WindowThread_Nova_window);
	}
	
	return this;
}

void nova_standard_star_Nova_WindowThread_Nova_destroy(nova_standard_star_Nova_WindowThread** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_star_Nova_Window_Nova_destroy(&(*this)->prv->nova_standard_star_Nova_WindowThread_Nova_window, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_star_Nova_WindowThread_1_Nova_this(nova_standard_star_Nova_WindowThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_star_Nova_Window* nova_standard_star_Nova_WindowThread_Nova_window)
{
	this->prv->nova_standard_star_Nova_WindowThread_Nova_window = nova_standard_star_Nova_WindowThread_Nova_window;
}

void nova_standard_star_Nova_WindowThread_Nova_run(nova_standard_star_Nova_WindowThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_createWindow((int)(this->prv->nova_standard_star_Nova_WindowThread_Nova_window->nova_standard_star_Nova_Window_Nova_x), (int)(this->prv->nova_standard_star_Nova_WindowThread_Nova_window->nova_standard_star_Nova_Window_Nova_y), (int)(this->prv->nova_standard_star_Nova_WindowThread_Nova_window->nova_standard_star_Nova_Window_Nova_width), (int)(this->prv->nova_standard_star_Nova_WindowThread_Nova_window->nova_standard_star_Nova_Window_Nova_height), (char*)(this->prv->nova_standard_star_Nova_WindowThread_Nova_window->nova_standard_star_Nova_Window_Nova_title->nova_standard_Nova_String_Nova_chars));
}

void nova_standard_star_Nova_WindowThread_0_Nova_super(nova_standard_star_Nova_WindowThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_star_Nova_WindowThread_Nova_window = (nova_standard_star_Nova_Window*)nova_null;
}

