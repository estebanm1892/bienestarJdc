<?php

namespace App\Http\Controllers;

use App\Activity;
use App\VirtualResource;
use Illuminate\Http\Request;
use File;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Support\Facades\Storage;

class VirtualResourceController extends Controller
{

    private $menu_item = 3;
    private $title_page = 'Actividades';

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */

    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create($id)
    {
        $activity   =   Activity::findOrFail($id);
        return view('admin.activities.virtual-resource.create', compact('activity'))
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, $id)
    {
        
        $activity   =   Activity::findOrFail($id);

        $rules = [
            'tittle'        =>  'required|min:4',
            'description'   =>  'required',
            'image.*'       =>  'image|mimes:jpg,jpge,png',
            'docs.*'        =>  'file|mimes:pdf|max:10000'
        ];

        $this->validate($request, $rules);

        $vresource = new VirtualResource();

        $vresource->tittle          =   $request->input('tittle');
        $vresource->description     =   $request->input('description');
        $vresource->video           =   $request->input('video');    

        if ($request->video != null) {
            $vresource->embed_video     =   "https://www.youtube.com/embed/".$vresource->id_youtube($request->video)."?rel=0";
        }
        $vresource->activity_id     =   $activity->id;
        

        $vresource->save();

        if ($request->file('image')) {
            $file       =   $request->file('image');
            $nameImg    =   'bienestar_recurso'.$vresource->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/img/recursos/';
            $file->move($path, $nameImg);

            $vresource->image = '/img/recursos/'.$nameImg;
            $vresource->save();
        }

        if ($request->file('docs')) {
            $file       =   $request->file('docs');
            $nameDoc    =   'bienestar_recursos_virtuales'.$vresource->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/docs/recursos_virtuales/';
            $file->move($path, $nameDoc);
            $vresource->docs = '/docs/recursos_virtuales/'.$nameDoc;
            $vresource->save();
        }

        return redirect()->route('actividades.edit', $id)->with('session_msg', 'Se ha creado el recurso virtual correctamente.');

    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    public function show_mobile($id)
    {
        try {
            $vresource = VirtualResource::findOrfail($id);
        } catch (ModelNotFoundException $e) {
            return response()->json([
                'error'     =>  'No existe un recurso virtual con Id: '.$id,
                'success'   =>  false,
            ], 404);
        }

        $vresource = $vresource->where('id', $id)
            ->select([
                'id',
                'tittle',
                'description',
                'embed_video',
                'docs',
                'image',
                'activity_id'
            ])
            ->get();

        return response()->json([
            'vresource'     =>  $vresource,
            'success'   =>  true,
        ]);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $vresource = VirtualResource::findOrFail($id);

        return view('admin.activities.virtual-resource.edit', compact('vresource'))
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {        
        $rules = [
            'tittle'        =>  'required|min:4',
            'description'   =>  'required',
            'image.*'       =>  'image|mimes:jpg,jpge,png',
            'docs.*'        =>  'file|mimes:pdf|max:10000'
        ];

        $this->validate($request, $rules);

        $vresource = VirtualResource::find($id);

        $vresource->tittle          =   $request->input('tittle');
        $vresource->description     =   $request->input('description');
        $vresource->video           =   $request->input('video');     

        if ($request->video != null) {
            $vresource->embed_video     =   "https://www.youtube.com/embed/".$vresource->id_youtube($request->video)."?rel=0";
        }

        $vresource->save();

        if ($request->file('image')) {
            if ($vresource->image != null) {
                if(file_exists(public_path().str_replace(env('APP_URL'), '/', $vresource->image))){
                    unlink(public_path().str_replace(env('APP_URL'), '/', $vresource->image));
                }
            }
            $file       =   $request->file('image');
            $nameImg    =   'bienestar_recurso'.$vresource->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/img/recursos/';
            $file->move($path, $nameImg);

            $vresource->image = '/img/recursos/'.$nameImg;
            $vresource->save();
        }

        if ($request->file('docs')) {
            if ($vresource->docs != null) {
                if(file_exists(public_path().str_replace(env('APP_URL'), '/', $vresource->docs))){
                    unlink(public_path().str_replace(env('APP_URL'), '/', $vresource->docs));
                }
            }
            $file       =   $request->file('docs');
            $nameDoc    =   'bienestar_recursos_virtuales'.$vresource->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/docs/recursos_virtuales/';
            $file->move($path, $nameDoc);

            $vresource->docs = '/docs/recursos_virtuales/'.$nameDoc;
            $vresource->save();
        }

        return redirect()->route('actividades.edit', $vresource->activity_id)->with('session_msg', 'Se ha editado el recurso virtual correctamente.');

    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $vresource  = VirtualResource::findOrFail($id);

        if ($vresource->image != null) {
            if(file_exists(public_path().str_replace(env('APP_URL'), '/', $vresource->image))){
                unlink(public_path().str_replace(env('APP_URL'), '/', $vresource->image));
            }
        }

        if ($vresource->docs != null) {
            if(file_exists(public_path().str_replace(env('APP_URL'), '/', $vresource->docs))){
                unlink(public_path().str_replace(env('APP_URL'), '/', $vresource->docs));
            }
        }        

        $vresource->delete();

        return redirect()->back()->with('session_msg', 'se ha eliminado correctamente el recurso virtual.');
    }
}
