#include <precompiled.h>
#include <nova/math/nova_math_Nova_Graph.h>



nova_math_Extension_VTable_Graph nova_math_Extension_VTable_Graph_val =
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


void nova_math_Nova_Graph_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_Nova_Graph* nova_math_Nova_Graph_Nova_construct(nova_math_Nova_Graph* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_math_Nova_Graph, this,);
	this->vtable = &nova_math_Extension_VTable_Graph_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_Nova_Graph_Nova_super(this, exceptionData);
	
	{
		nova_math_Nova_Graph_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_math_Nova_Graph_Nova_destroy(nova_math_Nova_Graph** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_math_Nova_Graph_0_Nova_this(nova_math_Nova_Graph* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_math_Nova_Graph_Nova_super(nova_math_Nova_Graph* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

