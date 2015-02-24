/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/ishida/git/HandOver/app/src/main/aidl/com/example/ishida/handover/IHandOverService.aidl
 */
package com.example.ishida.handover;
public interface IHandOverService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.ishida.handover.IHandOverService
{
private static final String DESCRIPTOR = "com.example.ishida.handover.IHandOverService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.ishida.handover.IHandOverService interface,
 * generating a proxy if needed.
 */
public static com.example.ishida.handover.IHandOverService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.ishida.handover.IHandOverService))) {
return ((com.example.ishida.handover.IHandOverService)iin);
}
return new com.example.ishida.handover.IHandOverService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.example.ishida.handover.IHandOverCallback _arg0;
_arg0 = com.example.ishida.handover.IHandOverCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
com.example.ishida.handover.IHandOverCallback _arg0;
_arg0 = com.example.ishida.handover.IHandOverCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
return true;
}
case TRANSACTION_handOver:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
java.util.Map _arg1;
ClassLoader cl = (ClassLoader)this.getClass().getClassLoader();
_arg1 = data.readHashMap(cl);
this.handOver(_arg0, _arg1);
return true;
}
case TRANSACTION_activityChanged:
{
data.enforceInterface(DESCRIPTOR);
this.activityChanged();
return true;
}
case TRANSACTION_readDictionary:
{
data.enforceInterface(DESCRIPTOR);
java.util.Map _result = this.readDictionary();
reply.writeNoException();
reply.writeMap(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.example.ishida.handover.IHandOverService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void registerCallback(com.example.ishida.handover.IHandOverCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void unregisterCallback(com.example.ishida.handover.IHandOverCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void handOver(String activityName, java.util.Map dictionary) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(activityName);
_data.writeMap(dictionary);
mRemote.transact(Stub.TRANSACTION_handOver, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void activityChanged() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_activityChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public java.util.Map readDictionary() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.Map _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readDictionary, _data, _reply, 0);
_reply.readException();
ClassLoader cl = (ClassLoader)this.getClass().getClassLoader();
_result = _reply.readHashMap(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_handOver = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_activityChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_readDictionary = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void registerCallback(com.example.ishida.handover.IHandOverCallback callback) throws android.os.RemoteException;
public void unregisterCallback(com.example.ishida.handover.IHandOverCallback callback) throws android.os.RemoteException;
public void handOver(String activityName, java.util.Map dictionary) throws android.os.RemoteException;
public void activityChanged() throws android.os.RemoteException;
public java.util.Map readDictionary() throws android.os.RemoteException;
}
