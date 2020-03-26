<?php

namespace App\Http\Controllers;

use App\Publication;
use App\PublicationStatus;
use App\Area;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Http\Request;

class PublicationController extends Controller
{
    

    private $menu_item = 4;
    private $title_page = 'Noticias';

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        if (auth()->user()->user_type_id == 1) {
            $publications = Publication::orderBy('id', 'DESC')->paginate(10);
        } elseif(auth()->user()->user_type_id == 2) {
            $publications = Publication::where('area_id', auth()->user()->area_id )->orderBy('id', 'DESC')->paginate(10);
        }
        
        return view('admin.publications.index', compact('publications'))
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    public function index_mobile()
    {
        $publications = Publication::where('new_status_id', 2)
            ->with(['area' => function($area){
                $area->select([
                    'id',
                    'name'
                ]);
            }])
            ->select([
                'id',
                'image',
                'tittle',
                'content',
                'area_id'
            ])
            ->orderby('id', 'DESC')->get();

        return response()->json([
            'publications'     =>  $publications,
            'success'   =>  true,
        ]);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        $statuses   = PublicationStatus::orderBy('name', 'DESC')->get();
        $areas      = Area::orderby('name', 'ASC')->get();

        return view('admin.publications.create')
        ->with('statuses', $statuses)
        ->with('areas', $areas)
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $rules = [
            'tittle'            =>  'required|min:4',
            'image.*'           =>  'image|mimes:jpg,jpge,png',
            'content'           =>  'required|',
            'area_id'           =>  'required|numeric',
            'new_status_id'     =>  'required|numeric'
        ];

        $this->validate($request, $rules);

        $publication = new Publication();

        $publication->tittle           =   $request->input('tittle');
        $publication->content          =   $request->input('content');
        $publication->image            =   '';
        $publication->area_id          =   $request->input('area_id');
        $publication->new_status_id    =   $request->input('new_status_id');
        
        $publication->save();

        if ($request->file('image')) {
            $file       =   $request->file('image');
            $nameImg    =   'bienestar_noticia_'.$publication->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/img/noticias/';
            $file->move($path, $nameImg);
            $publication->image = '/img/noticias/'.$nameImg;
            $publication->save();
        }

        return redirect()->route('noticias.index')->with('session_msg', 'Se ha creado correctamente la noticia.');
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
            $publication = Publication::findOrfail($id);
        } catch (ModelNotFoundException $e) {
            return response()->json([
                'error'     =>  'No existe una noticia con Id: '.$id,
                'success'   =>  false,
            ], 404);
        }
        if($publication->new_status_id == 1){
            return response()->json([
                'error'     =>  'No existe una noticia con Id: '.$id,
                'success'   =>  false,
            ], 404);
        }else{        
            $publication = $publication->where('id', $id)            
                ->select([
                    'id',
                    'image',
                    'tittle',
                    'content',
                    'area_id'
                ])
                ->with(['area' => function($area){
                    $area->select([
                        'id',
                        'name'
                    ]);
                }])
                ->orderby('id', 'DESC')->get();

            return response()->json([
                'publication'     =>  $publication,
                'success'   =>  true,
            ]);
        }
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $publication = Publication::findOrFail($id);
        $statuses    = PublicationStatus::orderBy('name', 'DESC')->get();
        $myStatus    = PublicationStatus::find($publication->new_status_id);
        $areas       = Area::orderby('name', 'ASC')->get();
        $myArea      = Area::find($publication->area_id);

        return view('admin.publications.edit', compact('publication'))
        ->with('statuses', $statuses)
        ->with('myStatus', $myStatus)
        ->with('areas', $areas)
        ->with('myArea', $myArea)
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
            'tittle'            =>  'required|min:4',
            'image.*'           =>  'image|mimes:jpg,jpge,png',
            'content'           =>  'required|',
            'area_id'           =>  'required|numeric',
            'new_status_id'     =>  'required|numeric'
        ];

        $this->validate($request, $rules);

        $publication = Publication::find($id);

        $publication->tittle           =   $request->input('tittle');
        $publication->content          =   $request->input('content');
        $publication->area_id          =   $request->input('area_id');
        $publication->new_status_id    =   $request->input('new_status_id');

        $publication->save();

        if ($request->file('image')) {
            if ($publication->image != null) {
                if(file_exists(public_path().str_replace(env('APP_URL'), '/', $publication->image))){
                    unlink(public_path().str_replace(env('APP_URL'), '/', $publication->image));
                }
            }
            $file       =   $request->file('image');
            $nameImg    =   'bienestar_noticia_'.$publication->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/img/noticias/';
            $file->move($path, $nameImg);
            $publication->image = '/img/noticias/'.$nameImg;
            $publication->save();
        }

         return redirect()->route('noticias.index')->with('session_msg', 'Se ha editado correctamente la noticia.');

    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $publication  = Publication::findOrFail($id);

        if ($publication->image != null) {
            if(file_exists(public_path().str_replace(env('APP_URL'), '/', $publication->image))){
                unlink(public_path().str_replace(env('APP_URL'), '/', $publication->image));
            }
        }

        $publication->delete();

        return redirect()->back()->with('session_msg', 'se ha eliminado correctamente la noticia.');
    }
}
