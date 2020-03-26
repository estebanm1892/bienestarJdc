<?php

namespace App\Http\Controllers;

use App\Normative;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Http\Request;

class NormativeController extends Controller
{
    
    private $menu_item = 6;
    private $title_page = 'Normativas';

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $normatives = Normative::orderBy('id', 'DESC')->paginate(10);

        return view('admin.normatives.index', compact('normatives'))
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    public function index_mobile()
    {
        $normatives = Normative::orderBy('id', 'DESC')
            ->select([
                'id',
                'tittle'
            ])
            ->get();

        return response()->json([
            'normatives'     =>  $normatives,
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
        return view('admin.normatives.create')
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
            'tittle'        =>  'required|min:4',
            'document.*'        =>  'file|mimes:pdf|max:10000'
        ];

        $this->validate($request, $rules);

        $normative = new Normative();

        $normative->tittle          =   $request->input('tittle');
        $normative->document        =   '';

        $normative->save();

        if ($request->file('document')) {
            $file       =   $request->file('document');
            $nameDoc    =   'bienestar_normativas_'.$normative->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/docs/normativas/';
            $file->move($path, $nameDoc);
            $normative->document = '/docs/normativas/'.$nameDoc;
            $normative->save();
        }

        return redirect()->route('normativas.index')->with('session_msg', 'Se ha creado correctamente la normativa.');
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
            $normative = Normative::findOrfail($id);
        } catch (ModelNotFoundException $e) {
            return response()->json([
                'error'     =>  'No existe una normativa con Id: '.$id,
                'success'   =>  false,
            ], 404);
        }

        $normative = $normative->where('id', $id)
            ->select([
                'id',
                'tittle',
                'document'
            ])
            ->get();

        return response()->json([
            'normative'     =>  $normative,
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
        $normative = Normative::findOrFail($id);

        return view('admin.normatives.edit', compact('normative'))
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
            'document.*'        =>  'file|mimes:pdf|max:10000'
        ];

        $this->validate($request, $rules);

        $normative = Normative::find($id);

        $normative->tittle          =   $request->input('tittle');

        $normative->save();

        if ($request->file('document')) {
            if ($normative->document != null) {
                if(file_exists(public_path().str_replace(env('APP_URL'), '/', $normative->document))){
                    unlink(public_path().str_replace(env('APP_URL'), '/', $normative->document));
                }
            }

            $file       =   $request->file('document');
            $nameDoc    =   'bienestar_normativas_'.$normative->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/docs/normativas/';
            $file->move($path, $nameDoc);
            $normative->document = '/docs/normativas/'.$nameDoc;
            $normative->save();
        }

        return redirect()->route('normativas.index')->with('session_msg', 'Se ha editado correctamente la normativa.');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $normative  = Normative::findOrFail($id);

        if ($normative->document != null) {
            if(file_exists(public_path().str_replace(env('APP_URL'), '/', $normative->document))){
                unlink(public_path().str_replace(env('APP_URL'), '/', $normative->document));
            }
        }  

        $normative->delete();   

        return redirect()->back()->with('session_msg', 'se ha eliminado correctamente la normativa.');
    }
}
