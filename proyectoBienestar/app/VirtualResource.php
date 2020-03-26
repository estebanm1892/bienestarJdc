<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class VirtualResource extends Model
{
    protected $table = 'virtual_resources';
    protected $fillable = [
        'tittle', 'description', 'docs', 'video', 'embed_video', 'image', 'activitiy_id',
    ];

    public function activities()
    {
    	return $this->hasMany('App\Activity');
    }

    public function id_youtube($url) {
        $patron = '%^ (?:https?://)? (?:www\.)? (?: youtu\.be/ | youtube\.com (?: /embed/ | /v/ | /watch\?v= ) ) ([\w-]{10,12}) $%x';
        $array = preg_match($patron, $url, $parte);
        if (false !== $array) {
            return $parte[1];
        }
        return false;
    }
}
