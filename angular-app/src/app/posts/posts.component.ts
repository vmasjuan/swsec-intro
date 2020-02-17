import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

  posts: Object;
  postForm = new FormGroup({
    mensaje: new FormControl('')
  });

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.getPosts();
  }

  getPosts() {
    this.api.getPosts().subscribe(
      data => {
        this.posts = data;
        console.log(this.posts);
      },
      error => {
        console.log(error);
      }
      );
  }

  insertPost(message) {
    this.api.insertPost(message).subscribe(
      data => {
        console.log(data);
        this.getPosts();
        this.postForm.reset();
      },
      error => {
        console.log(error);
      }
    );
  }

  onSubmit() {
    this.insertPost(this.postForm.value.mensaje);
  }

}
