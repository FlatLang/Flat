#include <precompiled.h>
#include <compiler/tree/node/annotation/compiler_tree_node_annotation_Nova_Annotation.h>



compiler_tree_node_annotation_Extension_VTable_Annotation compiler_tree_node_annotation_Extension_VTable_Annotation_val =
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
	compiler_tree_node_annotation_Nova_Annotation_1_Nova_clone,
};


CCLASS_PRIVATE
(
	compiler_util_Nova_Location* compiler_tree_node_Nova_Node_Nova_locationIn;
	compiler_tree_node_Nova_Node* compiler_tree_node_Nova_Node_Nova_parent;
	nova_datastruct_list_Nova_Array* compiler_tree_node_Nova_Node_Nova_children;
	nova_datastruct_list_Nova_Array* compiler_tree_node_Nova_Node_Nova_annotations;
	
)
void compiler_tree_node_annotation_Nova_Annotation_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

compiler_tree_node_annotation_Nova_Annotation* compiler_tree_node_annotation_Nova_Annotation_Nova_construct(compiler_tree_node_annotation_Nova_Annotation* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_tree_node_annotation_Nova_Annotation, this);
	this->vtable = &compiler_tree_node_annotation_Extension_VTable_Annotation_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_tree_node_Nova_Node_Nova_super((compiler_tree_node_Nova_Node*)this, exceptionData);
	compiler_tree_node_annotation_Nova_Annotation_0_Nova_super(this, exceptionData);
	
	{
		compiler_tree_node_annotation_Nova_Annotation_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_tree_node_annotation_Nova_Annotation_Nova_destroy(compiler_tree_node_annotation_Nova_Annotation** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

compiler_tree_node_annotation_Nova_Annotation* compiler_tree_node_annotation_Nova_Annotation_1_Nova_clone(compiler_tree_node_annotation_Nova_Annotation* this, nova_exception_Nova_ExceptionData* exceptionData, compiler_tree_node_Nova_Node* compiler_tree_node_annotation_Nova_Annotation_Nova_temporaryParent, compiler_util_Nova_Location* compiler_tree_node_annotation_Nova_Annotation_Nova_locationIn, char compiler_tree_node_annotation_Nova_Annotation_Nova_cloneChildren)
{
	return (compiler_tree_node_annotation_Nova_Annotation*)nova_null;
}

void compiler_tree_node_annotation_Nova_Annotation_0_Nova_this(compiler_tree_node_annotation_Nova_Annotation* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_tree_node_annotation_Nova_Annotation_0_Nova_super(compiler_tree_node_annotation_Nova_Annotation* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

