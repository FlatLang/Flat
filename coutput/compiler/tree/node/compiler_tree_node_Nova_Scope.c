#include <precompiled.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Scope.h>



compiler_tree_node_Extension_VTable_Scope compiler_tree_node_Extension_VTable_Scope_val =
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
	compiler_tree_node_Nova_Scope_1_Nova_clone,
};


CCLASS_PRIVATE
(
	compiler_util_Nova_Location* compiler_tree_node_Nova_Node_Nova_locationIn;
	compiler_tree_node_Nova_Node* compiler_tree_node_Nova_Node_Nova_parent;
	nova_datastruct_list_Nova_Array* compiler_tree_node_Nova_Node_Nova_children;
	nova_datastruct_list_Nova_Array* compiler_tree_node_Nova_Node_Nova_annotations;
	
	int compiler_tree_node_Nova_Scope_Nova_id;
	int compiler_tree_node_Nova_Scope_Nova_localVariableID;
	nova_datastruct_list_Nova_Array* compiler_tree_node_Nova_Scope_Nova_listeners;
	
)
void compiler_tree_node_Nova_Scope_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

compiler_tree_node_Nova_Scope* compiler_tree_node_Nova_Scope_Nova_construct(compiler_tree_node_Nova_Scope* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_tree_node_Nova_Scope, this);
	this->vtable = &compiler_tree_node_Extension_VTable_Scope_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_tree_node_Nova_Node_Nova_super((compiler_tree_node_Nova_Node*)this, exceptionData);
	compiler_tree_node_Nova_Scope_0_Nova_super(this, exceptionData);
	
	{
		compiler_tree_node_Nova_Scope_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_tree_node_Nova_Scope_Nova_destroy(compiler_tree_node_Nova_Scope** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	nova_datastruct_list_Nova_Array_Nova_destroy(&(*this)->prv->compiler_tree_node_Nova_Scope_Nova_listeners, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

compiler_tree_node_Nova_Scope* compiler_tree_node_Nova_Scope_1_Nova_clone(compiler_tree_node_Nova_Scope* this, nova_exception_Nova_ExceptionData* exceptionData, compiler_tree_node_Nova_Node* compiler_tree_node_Nova_Scope_Nova_temporaryParent, compiler_util_Nova_Location* compiler_tree_node_Nova_Scope_Nova_locationIn, char compiler_tree_node_Nova_Scope_Nova_cloneChildren)
{
	return (compiler_tree_node_Nova_Scope*)nova_null;
}

void compiler_tree_node_Nova_Scope_0_Nova_this(compiler_tree_node_Nova_Scope* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_tree_node_Nova_Scope_0_Nova_super(compiler_tree_node_Nova_Scope* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->compiler_tree_node_Nova_Scope_Nova_id = 0;
	this->prv->compiler_tree_node_Nova_Scope_Nova_localVariableID = 0;
	this->prv->compiler_tree_node_Nova_Scope_Nova_listeners = (nova_datastruct_list_Nova_Array*)nova_null;
}

