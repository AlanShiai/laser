/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class NativeClass */

#ifndef _Included_NativeClass
#define _Included_NativeClass
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     NativeClass
 * Method:    openDevice
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_NativeClass_openDevice
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectBlockEts
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_collectBlockEts
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectBlockImmediate
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_collectBlockImmediate
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectBlockTriggered
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_collectBlockTriggered
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectRapidBlock
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_collectRapidBlock
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectStreamingImmediate
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_collectStreamingImmediate
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectStreamingTriggered
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_collectStreamingTriggered
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    closeDevice
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_NativeClass_closeDevice
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectBlockEtsArray
 * Signature: ()[D
 */
JNIEXPORT jintArray JNICALL Java_NativeClass_collectBlockEtsArray
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectBlockImmediateArray
 * Signature: ()[D
 */
JNIEXPORT jintArray JNICALL Java_NativeClass_collectBlockImmediateArray
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectBlockTriggeredArray
 * Signature: ()[D
 */
JNIEXPORT jintArray JNICALL Java_NativeClass_collectBlockTriggeredArray
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectRapidBlockArray
 * Signature: ()[D
 */
JNIEXPORT jintArray JNICALL Java_NativeClass_collectRapidBlockArray
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectStreamingImmediateArray
 * Signature: ()[D
 */
JNIEXPORT jintArray JNICALL Java_NativeClass_collectStreamingImmediateArray
  (JNIEnv *, jobject);

/*
 * Class:     NativeClass
 * Method:    collectStreamingTriggeredArray
 * Signature: ()[D
 */
JNIEXPORT jintArray JNICALL Java_NativeClass_collectArray
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif