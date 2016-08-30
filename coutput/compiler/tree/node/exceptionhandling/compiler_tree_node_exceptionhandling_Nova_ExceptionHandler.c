#include <precompiled.h>
#include <compiler/tree/node/exceptionhandling/compiler_tree_node_exceptionhandling_Nova_ExceptionHandler.h>



compiler_tree_node_exceptionhandling_Extension_VTable_ExceptionHandler compiler_tree_node_exceptionhandling_Extension_VTable_ExceptionHandler_val =
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
		(void(*)(compiler_tree_node_Nova_Listenable*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*))compiler_tree_node_Nova_Node_Nova_onAdded,
		(void(*)(compiler_tree_node_annotation_Nova_Annotatable*, nova_exception_Nova_ExceptionData*, compiler_tree_node_annotation_Nova_Annotation*))compiler_tree_node_Nova_Node_Nova_addAnnotation,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	compiler_tree_node_Nova_Node_Nova_addAnnotation,
	compiler_tree_node_Nova_Node_Nova_onAdded,
	compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_1_Nova_clone,
};


CCLASS_PRIVATE
(
	compiler_util_Nova_Location* compiler_tree_node_Nova_Node_Nova_locationIn;
	compiler_tree_node_Nova_Node* compiler_tree_node_Nova_Node_Nova_parent;
	nova_datastruct_list_Nova_Array* compiler_tree_node_Nova_Node_Nova_children;
	nova_datastruct_list_Nova_Array* compiler_tree_node_Nova_Node_Nova_annotations;
	
)
void compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

compiler_tree_node_exceptionhandling_Nova_ExceptionHandler* compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_Nova_construct(compiler_tree_node_exceptionhandling_Nova_ExceptionHandler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_tree_node_exceptionhandling_Nova_ExceptionHandler, this);
	this->vtable = &compiler_tree_node_exceptionhandling_Extension_VTable_ExceptionHandler_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_tree_node_Nova_Node_Nova_super((compiler_tree_node_Nova_Node*)this, exceptionData);
	compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_0_Nova_super(this, exceptionData);
	
	{
		compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_Nova_destroy(compiler_tree_node_exceptionhandling_Nova_ExceptionHandler** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

compiler_tree_node_exceptionhandling_Nova_ExceptionHandler* compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_1_Nova_clone(compiler_tree_node_exceptionhandling_Nova_ExceptionHandler* this, nova_exception_Nova_ExceptionData* exceptionData, compiler_tree_node_Nova_Node* compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_Nova_temporaryParent, compiler_util_Nova_Location* compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_Nova_locationIn, char compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_Nova_cloneChildren)
{
	return (compiler_tree_node_exceptionhandling_Nova_ExceptionHandler*)nova_null;
}

void compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_0_Nova_this(compiler_tree_node_exceptionhandling_Nova_ExceptionHandler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_0_Nova_super(compiler_tree_node_exceptionhandling_Nova_ExceptionHandler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

