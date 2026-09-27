import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CartService } from '../../core/services/cart.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent implements OnInit {
  constructor(public cartService: CartService) {}

  ngOnInit(): void {
    this.cartService.refresh();
  }

  removeItem(cartItemId: number): void {
    this.cartService.remove(cartItemId).subscribe();
  }
}
