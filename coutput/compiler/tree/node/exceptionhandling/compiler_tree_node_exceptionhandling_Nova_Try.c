#include <precompiled.h>
#include <compiler/tree/node/exceptionhandling/compiler_tree_node_exceptionhandling_Nova_Try.h>



compiler_tree_node_exceptionhandling_Extension_VTable_Try compiler_tree_node_exceptionhandling_Extension_VTable_Try_val =
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
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


CCLASS_PRIVATE
(
	nova_datastruct_list_Nova_IntArray* compiler_tree_node_exceptionhandling_Nova_Try_Nova_codes;
	
)
nova_Nova_String* compiler_tree_node_exceptionhandling_Nova_Try_Nova_IDENTIFIER;
void compiler_tree_node_exceptionhandling_Nova_Try_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
		compiler_tree_node_exceptionhandling_Nova_Try_Nova_IDENTIFIER = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("try"));
	}
}

compiler_tree_node_exceptionhandling_Nova_Try* compiler_tree_node_exceptionhandling_Nova_Try_Nova_construct(compiler_tree_node_exceptionhandling_Nova_Try* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_tree_node_exceptionhandling_Nova_Try, this);
	this->vtable = &compiler_tree_node_exceptionhandling_Extension_VTable_Try_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_tree_node_exceptionhandling_Nova_Try_Nova_super(this, exceptionData);
	
	{
		compiler_tree_node_exceptionhandling_Nova_Try_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_tree_node_exceptionhandling_Nova_Try_Nova_destroy(compiler_tree_node_exceptionhandling_Nova_Try** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_datastruct_list_Nova_IntArray_Nova_destroy(&(*this)->prv->compiler_tree_node_exceptionhandling_Nova_Try_Nova_codes, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void compiler_tree_node_exceptionhandling_Nova_Try_0_Nova_this(compiler_tree_node_exceptionhandling_Nova_Try* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_tree_node_exceptionhandling_Nova_Try_Nova_super(compiler_tree_node_exceptionhandling_Nova_Try* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->compiler_tree_node_exceptionhandling_Nova_Try_Nova_codes = (nova_datastruct_list_Nova_IntArray*)nova_null;
}

